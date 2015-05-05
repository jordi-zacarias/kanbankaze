package com.inscrum.model.company

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by jordi on 29/04/2015.
 */

case class Team(id: Int, name: String, avatar: String, companyId: Int)

class TeamTable (tag: Tag) extends Table[Team](tag, "COMPANY_TEAM") {

  def id: Column[Int] = column[Int]("team_id", O.PrimaryKey, O.AutoInc)
  def name: Column[String] = column[String]("name")
  def avatar: Column[String] = column[String]("avatar")
  def companyId: Column[Int] = column[Int]("company_id")

  def * = (id, name, avatar, companyId) <> (Team.tupled, Team.unapply _)
}