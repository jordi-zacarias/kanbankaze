package com.inscrum.service.board

import com.inscrum.repository.board.RelBoardColumnTaskRepository
import com.inscrum.model.RelBoardColumnTask
import com.inscrum.repository.DB

/**
 * Created by jordi on 13/04/2015.
 */
object RelBoardColumnTaskService {

  def save(relBoardColumnTask: RelBoardColumnTask) = {
    DB { implicit session =>
      RelBoardColumnTaskRepository.save(relBoardColumnTask)
    }
  }

  def update(relBoardColumnTask: RelBoardColumnTask): Unit = {
    DB { implicit session =>
      RelBoardColumnTaskRepository.update(relBoardColumnTask)
    }
  }
}
