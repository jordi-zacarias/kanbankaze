package com.inscrum.service.board

import com.inscrum.model.BoardColumn
import com.inscrum.repository.DB
import com.inscrum.repository.board.BoardColumnRepository

/**
 * Created by jordi on 01/04/2015.
 */
object BoardColumnService {

  def getColumn(id: Int): Option[BoardColumn] = {
    DB { implicit session =>
      BoardColumnRepository.getColumn(id)
    }
  }
  def getColumnsByBoard(boardId: Int): List[BoardColumn] = {
    DB { implicit session =>
      BoardColumnRepository.getColumnsByBoard(boardId)
    }
  }
}
