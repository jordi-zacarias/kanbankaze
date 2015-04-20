package com.inscrum.repository.task

import java.util.UUID

import com.inscrum.model._
import com.inscrum.model.task._
import scala.slick.lifted.TableQuery
import scala.slick.driver.MySQLDriver.simple._


/**
 * Created by jordi on 03/04/2015.
 */
object TaskRepository {

  private val tasks = TableQuery[TaskTable]
  private val boardColumns = TableQuery[BoardColumnTable]
  private val relBoardColumnTask = TableQuery[RelBoardColumnTaskTable]
  private val relTaskUsers = TableQuery[RelTaskUserTable]

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
        rct <- relBoardColumnTask if (rct.taskId === t.id)
        c <- boardColumns if (c.id === rct.boardColumnId && c.boardId === boardId)
      } yield (t, rct)
    ).sortBy( r => (r._2.boardColumnId, r._2.position)).list
  }

  def getTaskByColumn(columnId: Int)(implicit s: Session) : List[(Task, RelBoardColumnTask)] = {
    (
      for{
        t <- tasks
        rct <- relBoardColumnTask if (rct.taskId === t.id && rct.boardColumnId === columnId)
      } yield (t, rct)
      ).sortBy( r => (r._2.position.asc)).list
  }

  def addUser(taskId: Int, userGuid: UUID)(implicit s: Session): Unit ={
    val taskUser = RelTaskUser(taskId, userGuid)
    relTaskUsers += taskUser
  }
}
