package com.inscrum.repository.board

import scala.slick.lifted.TableQuery
import scala.slick.driver.MySQLDriver.simple._

import com.inscrum.model._

object BoardRepository {
  
  private val boards = TableQuery[BoardTable]
  
  def getBoard (id: Int)(implicit s: Session) : Option[Board]  = boards.filter( _.id === id).firstOption
  def getAll()(implicit s: Session) : List[Board] = boards.list

}