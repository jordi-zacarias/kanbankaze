package com.inscrum.repository.task

import java.util.UUID

import com.inscrum.model._
import com.inscrum.model.task._
import com.inscrum.model.user._
import org.joda.time.DateTime
import scala.collection.immutable.ListMap
import scala.collection.mutable.Set
import scala.slick.ast.JoinType
import scala.slick.lifted.TableQuery
import scala.slick.driver.MySQLDriver.simple._


/**
 * Created by jordi on 03/04/2015.
 */
object TaskRepository {

  private val tasks = TableQuery[TaskTable]
  private val boardColumns = TableQuery[BoardColumnTable]
  private val relBoardColumnTasks = TableQuery[RelBoardColumnTaskTable]
  private val relTaskUsers = TableQuery[RelTaskUserTable]
  private val users = TableQuery[Users]

  def save(taskToSave: Task)(implicit s: Session) : Task = {

    (tasks returning tasks.map(_.id) into ((task, id) => task.copy(id, task.title, task.description, task.estimation, task.acceptanceCriteria, task.blocked, task.blockedReason))) += taskToSave
  }

  def delete(taskId : Int)(implicit s: Session) = {
    tasks.filter(t => t.id === taskId).delete
  }

  def getTask(id: Int)(implicit s: Session) : Option[Task] = tasks.filter( _.id === id).firstOption

  def getTaskByBoard(boardId: Int)(implicit s: Session) : List[(Task, RelBoardColumnTask)] = {
    (
      for{
        t <- tasks
        rct <- relBoardColumnTasks if (rct.taskId === t.id)
        c <- boardColumns if (c.id === rct.boardColumnId && c.boardId === boardId)
      } yield (t, rct)
    ).sortBy( r => (r._2.boardColumnId, r._2.position)).list
  }

  def getTaskByColumn(columnId: Int)(implicit s: Session) : List[Task] = {

    tasks
      .leftJoin(relTaskUsers.innerJoin(users).on(_.userGuid === _.guid)).on(_.id === _._1.taskId)
      .innerJoin(relBoardColumnTasks).on(_._1.id === _.taskId)
      .filter(_._2.boardColumnId === columnId)
      .map {
      case (((task, (taskUser, user)), boardColumn)) =>
        (task, (user.guid.?, user.firstName.?, user.lastName.?, user.email.?, user.avatar.?, boardColumn.position))
    }
      .mapResult {
      case (task: Task, (guid: Option[UUID], firstName: Option[String], lastName: Option[String], email: Option[String], avatar: Option[String], position: Int)) =>
        guid match {
          case None =>
            task.users = Some(Set.empty[UserSimple])
          case _ =>
            val userSimple = UserSimple(guid.get, firstName.get, lastName.get, email.get, avatar.get)
            task.users = Some(Set {
              userSimple
            })
        }
        task.position = position
        task
    }.list.groupBy(_.id).mapValues {
      taskWithSameId =>
        taskWithSameId.reduce { (previousTask, task) =>
          previousTask.users = Some(previousTask.users.get ++ task.users.get)
          previousTask
        }
    }
      .values
      .toList
  }

  def addUser(taskId: Int, userGuid: UUID)(implicit s: Session): Unit ={
    val taskUser = RelTaskUser(taskId, userGuid)
    relTaskUsers += taskUser
  }

  def removeUsers(taskId: Int)(implicit s: Session): Unit ={
    relTaskUsers.filter(tu => tu.taskId === taskId).delete
  }
}
