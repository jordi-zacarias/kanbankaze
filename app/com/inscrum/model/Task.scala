package com.inscrum.model

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by jordi on 28/03/2015.
 */

case class Task(id: Int, title: String, description: String, estimation: Int, acceptanceCriteria: String)

class TaskTable (tag: Tag) extends Table[Task](tag, "TASK") {

  def id: Column[Int] = column[Int]("task_id", O.PrimaryKey, O.AutoInc)
  def title: Column[String] = column[String]("title")
  def description: Column[String] = column[String]("description")
  def estimation: Column[Int] = column[Int]("estimation")
  def acceptanceCriteria: Column[String] = column[String]("acceptance_criteria")

  def * = (id, title, description, estimation, acceptanceCriteria) <> (Task.tupled, Task.unapply _)
    
}
