package com.inscrum.model.company

import java.util.UUID
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by jordi on 29/04/2015.
 */
case class RelTeamUser(teamId: Int, userGuid: UUID, roleId: Int)

class RelTeamUserTable (tag: Tag) extends Table[RelTeamUser](tag, "COMPANY_TEAM_USER") {
  def teamId: Column[Int] = column[Int]("team_id")
  def userGuid: Column[UUID] = column[UUID]("user_guid")
  def roleId: Column[Int] = column[Int]("role_id")

  def * = (teamId, userGuid, roleId) <> (RelTeamUser.tupled, RelTeamUser.unapply _)
}