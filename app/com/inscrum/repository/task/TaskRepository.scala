package com.inscrum.repository.task

import java.util.UUID

import com.inscrum.model._
import com.inscrum.model.task._
import com.inscrum.model.user._
import org.joda.time.DateTime
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

  def getTaskByColumn(columnId: Int)(implicit s: Session) : List[(Task, User)] = { //List[((Task, RelBoardColumnTask), Seq[User])] = {

        (
          for{
            task <- tasks leftJoin relTaskUsers on (_.id === _.taskId)
            user <- users if (user.guid === task._2.userGuid)
            boardColumnTask <- relBoardColumnTasks if (boardColumnTask.taskId === task._1.id && boardColumnTask.boardColumnId === columnId)
          } yield (task._1, user, boardColumnTask)
        ).sortBy( r => r._3.position.asc).map( r => (r._1,r._2)).list
  }

  def getTaskByColumn2(columnId: Int)(implicit s: Session) : List[(Task, Option[User])] = { //List[((Task, RelBoardColumnTask), Seq[User])] = {

    (
      for{
        (task,( relUsers, user)) <- tasks leftJoin (relTaskUsers join users on (_.userGuid === _.guid)) on (_.id === _._1.taskId)
        (boardColumnTask) <- relBoardColumnTasks if (boardColumnTask.taskId === task.id && boardColumnTask.boardColumnId === columnId)
      } yield (task, user.?, boardColumnTask)
    ).sortBy( r => r._3.position.asc).map( r => (r._1,r._2)).list
  }

  def addUser(taskId: Int, userGuid: UUID)(implicit s: Session): Unit ={
    val taskUser = RelTaskUser(taskId, userGuid)
    relTaskUsers += taskUser
  }
}
