package com.inscrum.service.board

import com.inscrum.repository.board.BoardRepository
import com.inscrum.model.Board
import com.inscrum.repository.DB


object BoardService {
  
  def getBoard (id: Int): Option[Board] = {
    DB { implicit session =>
        return BoardRepository.getBoard(id)
    }
    
  } 
  def getAll(): List[Board] = {
    DB { implicit session =>
        return BoardRepository.getAll()
    }
  }

}