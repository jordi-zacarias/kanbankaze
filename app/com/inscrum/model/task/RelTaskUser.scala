package com.inscrum.model.task

import java.util.UUID
import scala.slick.driver.MySQLDriver.simple._


/**
 * Created by jordi on 20/04/2015.
 */
case class RelTaskUser(taskId: Int, userGuid : UUID)

class RelTaskUserTable (tag: Tag) extends Table[RelTaskUser](tag, "TASK_USER") {

  def taskId: Column[Int] = column[Int]("task_id")
  def userGuid: Column[UUID] = column[UUID]("user_guid")

  def pk = primaryKey("pkRelBoardColumnTask", (taskId, userGuid))

  def * = (taskId, userGuid) <> (RelTaskUser.tupled, RelTaskUser.unapply _)

}