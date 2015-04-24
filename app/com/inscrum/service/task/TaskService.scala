package com.inscrum.service.task

import java.util.UUID

import com.inscrum.model.RelBoardColumnTask
import com.inscrum.model.task._
import com.inscrum.model.user.User
import com.inscrum.repository.board.RelBoardColumnTaskRepository
import com.inscrum.repository.task.TaskRepository
import com.inscrum.repository.DB
import scala.collection.immutable.ListMap


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

//  def getTaskByColumn(columnId: Int) : List[(Task, RelBoardColumnTask)] = {
//    DB { implicit session =>
//      TaskRepository.getTaskByColumn(columnId)
//    }
//  }

  def getTaskByColumn(columnId: Int) : List[(Task, Seq[User])] = {
    DB { implicit session =>
      val taskByColumn = TaskRepository.getTaskByColumn(columnId)

      taskByColumn.foldLeft(ListMap.empty[Task, Seq[User]]) {
        case (theMap, (task, newUser)) => {

          val listUsers = theMap.get(task) match {
            case None => Seq(newUser)
            case Some(existingUsers) => existingUsers :+ newUser
          }

          theMap + ((task, listUsers))
        }
      }.toList
    }
  }

  def getTaskByColumn2(columnId: Int) : List[(Task, Seq[Option[User]])] = {
    DB { implicit session =>
      //TaskRepository.getTaskByColumn2(columnId)
      val taskByColumn = TaskRepository.getTaskByColumn2(columnId)

      taskByColumn.foldLeft(ListMap.empty[Task, Seq[Option[User]]]) {
        case (theMap, (task, newUser)) => {

          val listUsers = theMap.get(task) match {
            case None => Seq(newUser)
            case Some(existingUsers) => existingUsers :+ newUser
          }

          theMap + ((task, listUsers))
        }
      }.toList
    }
  }

  def addUser(taskUser: RelTaskUser) = {
    DB { implicit session =>
      TaskRepository.addUser(taskUser.taskId, taskUser.userGuid)
    }
  }

}
