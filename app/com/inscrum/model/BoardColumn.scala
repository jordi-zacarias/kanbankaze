package com.inscrum.model

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.lifted.ProvenShape



/**
 * Created by jordi on 28/03/2015.
 */

case class BoardColumn(id: Int, title: String, finalStatus: Boolean, boardId: Int, position: Int)

class BoardColumnTable (tag: Tag) extends Table[BoardColumn](tag, "BOARD_COLUMN") {

  lazy val boardTable = TableQuery[BoardTable]
  
  def id: Column[Int] = column[Int]("boardColumn_id", O.PrimaryKey, O.AutoInc)
  def title: Column[String] = column[String]("title")
  def finalStatus: Column[Boolean] = column[Boolean]("final_status")
  def boardId: Column[Int] = column[Int]("board_id")
  def position: Column[Int] = column[Int]("position")

  def * = (id, title, finalStatus, boardId, position) <> (BoardColumn.tupled, BoardColumn.unapply _)
  
  def board = foreignKey("board", boardId, boardTable)(_.id)
    
}
