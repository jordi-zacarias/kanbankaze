package com.inscrum.model

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.lifted.ProvenShape

/**
 * Created by jordi on 28/03/2015.
 */

case class Board(id: Int, title: String, description: String)

class BoardTable (tag: Tag) extends Table[Board](tag, "BOARD") {

  def id: Column[Int] = column[Int]("board_id", O.PrimaryKey, O.AutoInc)
  def title: Column[String] = column[String]("title")
  def description: Column[String] = column[String]("description")

  def * = (id, title, description) <> (Board.tupled, Board.unapply _)
    
}