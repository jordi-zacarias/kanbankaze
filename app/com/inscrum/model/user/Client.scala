package com.inscrum.model.user

import scala.slick.driver.MySQLDriver.simple._


case class Client(id: String, secret: Option[String], redirectUri: Option[String], scope: Option[String])

class Clients(tag: Tag) extends Table[Client](tag, "user_clients") {
  def id = column[String]("id", O.PrimaryKey)
  def secret = column[Option[String]]("secret")
  def redirectUri = column[Option[String]]("redirect_uri")
  def scope = column[Option[String]]("scope")
  def * = (id, secret, redirectUri, scope) <> (Client.tupled, Client.unapply)
}

