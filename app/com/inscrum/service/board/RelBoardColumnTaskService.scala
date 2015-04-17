package com.inscrum.service.board

import com.inscrum.repository.board.RelBoardColumnTaskRepository
import com.inscrum.model.RelBoardColumnTask
import com.inscrum.model.BoardColumn
import com.inscrum.model.task.Task
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

  def deleteTask(taskId : Int): Unit ={
    DB { implicit session =>
      RelBoardColumnTaskRepository.deleteTask(taskId)
    }
  }

  def deleteColumn(columnId : Int) = {
    DB { implicit session =>
      RelBoardColumnTaskRepository.deleteColumn(columnId)
    }
  }
}
