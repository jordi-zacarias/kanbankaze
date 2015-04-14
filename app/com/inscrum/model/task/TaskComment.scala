package com.inscrum.model.task

import java.util.UUID

import scala.slick.driver.MySQLDriver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.MySQLJodaSupport._

/**
 * Created by jordi on 14/04/2015.
 */
case class TaskComment(id: Int, comment: String, createdAt: DateTime, taskId: Int,  userGuid: UUID)

class TaskCommentTable (tag: Tag) extends Table[TaskComment](tag, "TASK_COMMENT") {

  def id: Column[Int] = column[Int]("task_id", O.PrimaryKey, O.AutoInc)
  def comment: Column[String] = column[String]("comment")
  def createdAt: Column[DateTime] = column[DateTime]("created_at")
  def taskId: Column[Int] = column[Int]("task_id")
  def userGuid: Column[UUID] = column[UUID]("user_guid")

  def * = (id, comment, createdAt, taskId, userGuid) <> (TaskComment.tupled, TaskComment.unapply _)
}