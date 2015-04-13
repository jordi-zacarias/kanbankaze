package com.inscrum.repository.board

import com.inscrum.model.BoardColumnTable
import scala.slick.lifted.TableQuery
import com.inscrum.model.BoardColumn
import scala.slick.driver.MySQLDriver.simple._

object BoardColumnRepository {
  
  val columns = TableQuery[BoardColumnTable]
  
  def getColumn(id: Int)(implicit s: Session) : Option[BoardColumn] = columns.filter( _.id === id).firstOption
  def getColumnsByBoard(boardId: Int)(implicit s: Session) : List[BoardColumn] = columns.sortBy( _.position.asc).list

}