package com.inscrum.model.user

import java.util.UUID

import scala.slick.driver.MySQLDriver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.MySQLJodaSupport._

case class AuthCode(authorizationCode: String, userGuid: UUID, redirectUri: Option[String], createdAt: DateTime, scope: Option[String], clientId: Option[String], expiresIn: Int)

class AuthCodes(tag: Tag) extends Table[AuthCode](tag, "user_auth_codes") {
  def authorizationCode = column[String]("authorization_code", O.PrimaryKey)
  def userGuid = column[UUID]("user_guid")
  def redirectUri = column[Option[String]]("redirect_uri")
  def createdAt = column[DateTime]("created_at")
  def scope = column[Option[String]]("scope")
  def clientId = column[Option[String]]("client_id")
  def expiresIn = column[Int]("expires_in")
  def * = (authorizationCode, userGuid, redirectUri, createdAt, scope, clientId, expiresIn) <> (AuthCode.tupled, AuthCode.unapply)
}


