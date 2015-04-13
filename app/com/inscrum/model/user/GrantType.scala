package com.inscrum.model.user

import java.util.UUID

import scala.slick.driver.MySQLDriver.simple._

case class GrantType(id: Int, grantType: String)

class GrantTypes(tag: Tag) extends Table[GrantType](tag, "user_grant_types") {
  def id = column[Int]("id", O.PrimaryKey)
  def grantType = column[String]("grant_type")
  def * = (id, grantType) <> (GrantType.tupled, GrantType.unapply)
}