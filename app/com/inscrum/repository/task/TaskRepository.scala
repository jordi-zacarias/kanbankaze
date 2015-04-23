package com.inscrum.repository.task

import java.util.UUID

import com.inscrum.model._
import com.inscrum.model.task._
import com.inscrum.model.user._
import scala.collection.immutable.ListMap
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

  def getTaskByColumn(columnId: Int)(implicit s: Session) : List[(Task, RelBoardColumnTask)] = {
    (
      for{
        t <- tasks
        rct <- relBoardColumnTasks if (rct.taskId === t.id && rct.boardColumnId === columnId)
      } yield (t, rct)
      ).sortBy( r => (r._2.position.asc)).list
  }

  def getTaskByColumn2(columnId: Int)(implicit s: Session) : List[((Task, RelBoardColumnTask), Seq[User])] = {

    val query = for{
          task <- tasks
          boardTask <- relBoardColumnTasks if (boardTask.taskId === task.id && boardTask.boardColumnId === columnId)
          taskUser <-  relTaskUsers if (taskUser.taskId === task.id)
          user <- relTaskUsers leftJoin users on ( _.userGuid === _.guid)
    } yield (task, boardTask, user._2)

    query.foldLeft(ListMap.empty[(Task, RelBoardColumnTask), Seq[User]]) {
      case (theMap, (task, boardTask, newUser)) => {

        val listUsers = theMap.get(task, boardTask) match {
          case None => Seq(newUser)
          case Some(existingUsers) => existingUsers :+ newUser
        }

        theMap + (((task, boardTask), listUsers))
      }
    }.toList

      //).sortBy( r => (r._2.position.asc)).list

    /*val query = for {
      (brand, license) ? Brands leftJoin Licenses on (_.id === _.brandId) if brand.code === code
      coordinator ? brand.coordinator
    } yield (brand, coordinator, license.id.?)

    query.list.groupBy { case (brand, coordinator, _) ? brand -> coordinator }
      .mapValues(_.flatMap(_._3)).map {
      case ((brand, coordinator), licenses) ?
        BrandView(brand, coordinator, licenses)
    }.toList.headOption*/
  }

  def addUser(taskId: Int, userGuid: UUID)(implicit s: Session): Unit ={
    val taskUser = RelTaskUser(taskId, userGuid)
    relTaskUsers += taskUser
  }
}
