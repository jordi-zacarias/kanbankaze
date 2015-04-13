package com.inscrum.model.user

import java.util.UUID

import scala.slick.driver.MySQLDriver.simple._

case class ClientGrantType(clientId: String, grantTypeId: Int)

class ClientGrantTypes(tag: Tag) extends Table[ClientGrantType](tag, "user_client_grant_types") {
  def clientId = column[String]("client_id")
  def grantTypeId = column[Int]("grant_type_id")
  def * = (clientId, grantTypeId) <> (ClientGrantType.tupled, ClientGrantType.unapply)
  val pk = primaryKey("pk_client_grant_type", (clientId, grantTypeId))
}