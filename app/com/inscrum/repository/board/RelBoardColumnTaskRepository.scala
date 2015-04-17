package com.inscrum.repository.board

import scala.slick.lifted.TableQuery
import scala.slick.driver.MySQLDriver.simple._
import com.inscrum.model._
import com.inscrum.model.task._

/**
 * Created by jordi on 13/04/2015.
 */
object RelBoardColumnTaskRepository {

  private val columnTasks = TableQuery[RelBoardColumnTaskTable]

  def save(relBoardColumnTask: RelBoardColumnTask)(implicit s: Session) = columnTasks += relBoardColumnTask

  def update(relBoardColumnTask: RelBoardColumnTask)(implicit s: Session): Unit ={
    columnTasks.filter(_.taskId === relBoardColumnTask.taskId)
    .map( c => (c.boardColumnId, c.position))
    .update(relBoardColumnTask.boardColumnId, relBoardColumnTask.position)
  }

  def deleteTask(taskId : Int)(implicit s: Session): Unit ={
    columnTasks.filter(ct => ct.taskId === taskId).delete
  }

  def deleteColumn(columnId : Int)(implicit s: Session) = {
    columnTasks.filter(ct => ct.boardColumnId === columnId).delete
  }
}
