package com.inscrum.model

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.lifted.PrimaryKey

/**
 * Created by jordi on 03/04/2015.
 */

case class RelBoardColumnTask (boardColumnId: Int, taskId: Int, position: Int)

class RelBoardColumnTaskTable (tag: Tag) extends Table[RelBoardColumnTask](tag, "BOARD_COLUMN_TASK") {

  lazy val boardColumnTable = TableQuery[BoardColumnTable]
  lazy val taskTable = TableQuery[TaskTable]

  def boardColumnId: Column[Int] = column[Int]("boardColumn_id")
  def taskId: Column[Int] = column[Int]("task_id")
  def position: Column[Int] = column[Int]("position")

  def pk = primaryKey("pkRelBoardColumnTask", (boardColumnId, taskId))

  def * = (boardColumnId, taskId, position) <> (RelBoardColumnTask.tupled, RelBoardColumnTask.unapply _)

  def boardColumn = foreignKey("boardColumn", boardColumnId, boardColumnTable)(_.id)
  def task = foreignKey("task", taskId, taskTable)(_.id)
}


