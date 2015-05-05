package com.inscrum.model.task

import java.util.UUID

import scala.slick.driver.MySQLDriver.simple._
import com.inscrum.model.user.UserSimple
import scala.collection.mutable.Set
/**
 * Created by jordi on 28/03/2015.
 */

case class Task(id: Int,
                title: String,
                description: String,
                estimation: Int,
                acceptanceCriteria: String,
                blocked: Boolean,
                blockedReason: Option[String]){

                var users: Option[Set[UserSimple]] = None
                var position: Int = 0
}

class TaskTable (tag: Tag) extends Table[Task](tag, "TASK") {

  def id: Column[Int] = column[Int]("task_id", O.PrimaryKey, O.AutoInc)
  def title: Column[String] = column[String]("title")
  def description: Column[String] = column[String]("description")
  def estimation: Column[Int] = column[Int]("estimation")
  def acceptanceCriteria: Column[String] = column[String]("acceptance_criteria")
  def blocked: Column[Boolean] = column[Boolean]("blocked")
  def blockedReason: Column[Option[String]] = column[Option[String]]("blocked_reason")

  def * = (id, title, description, estimation, acceptanceCriteria, blocked, blockedReason) <> (Task.tupled, Task.unapply _)

}