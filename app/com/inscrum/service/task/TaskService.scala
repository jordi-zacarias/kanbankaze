package com.inscrum.service.task

import com.inscrum.model.{RelBoardColumnTask, Task}
import com.inscrum.repository.task.TaskRepository
import com.inscrum.repository.DB


/**
 * Created by jordi on 03/04/2015.
 */
object TaskService {

  def getTask(id: Int) : Option[Task] = {
    DB { implicit session =>
      TaskRepository.getTask(id)
    }
  }
  def getTaskByBoard(boardId: Int) : List[(Task, RelBoardColumnTask)] = {
    DB { implicit session =>
      TaskRepository.getTaskByBoard(boardId)
    }
  }

  def getTaskByColumn(columnId: Int) : List[(Task, RelBoardColumnTask)] = {
    DB { implicit session =>
      TaskRepository.getTaskByColumn(columnId)
    }
  }

}
