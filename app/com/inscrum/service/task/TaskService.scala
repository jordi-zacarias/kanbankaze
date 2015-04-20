package com.inscrum.service.task

import java.util.UUID

import com.inscrum.model.RelBoardColumnTask
import com.inscrum.model.task._
import com.inscrum.repository.board.RelBoardColumnTaskRepository
import com.inscrum.repository.task.TaskRepository
import com.inscrum.repository.DB


/**
 * Created by jordi on 03/04/2015.
 */
object TaskService {

  def save(task: Task): Task ={
    DB{ implicit session =>
      TaskRepository.save(task)
    }
  }

  def delete(taskId: Int) = {
    DB{ implicit session =>
      session.withTransaction {
        RelBoardColumnTaskRepository.deleteTask(taskId)
        TaskRepository.delete(taskId)
      }
    }
  }

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

  def addUser(taskUser: RelTaskUser) = {
    DB { implicit session =>
      TaskRepository.addUser(taskUser.taskId, taskUser.userGuid)
    }
  }

}
