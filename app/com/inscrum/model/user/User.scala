package com.inscrum.model.user

import java.util.UUID

import scala.slick.driver.MySQLDriver.simple._
import org.joda.time.DateTime
import com.github.tototoshi.slick.HsqldbJodaSupport._

case class User(guid: UUID,
                firstName: String,
                lastName: String,
                email: String,
                password: String,
                avatar: String,
                salt: Option[String],
                createdAt: DateTime,
                createdByGuid: UUID,
                updatedAt: DateTime,
                updatedByGuid: UUID,
                deletedAt: Option[DateTime],
                deletedByGuid: Option[UUID])

case class UserSimple(guid: UUID,
                firstName: String,
                lastName: String,
                email: String,
                avatar: String)

class Users(tag: Tag) extends Table[User](tag, "user") {
  def guid = column[UUID]("guid", O.PrimaryKey)
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def email = column[String]("email")
  def password = column[String]("password")
  def avatar = column[String]("avatar")
  def salt = column[Option[String]]("salt")
  def createdAt = column[DateTime]("created_at")
  def createdByGuid = column[UUID]("created_by_guid")
  def updatedAt = column[DateTime]("updated_at")
  def updatedByGuid = column[UUID]("updated_by_guid")
  def deletedAt = column[Option[DateTime]]("deleted_at")
  def deletedByGuid = column[Option[UUID]]("deleted_by_guid")
  def * = (guid, firstName, lastName, email, password, avatar, salt, createdAt, createdByGuid, updatedAt, updatedByGuid, deletedAt, deletedByGuid) <> (User.tupled, User.unapply)
  def ? = (guid.?, firstName, lastName, email, password, avatar, salt, createdAt, createdByGuid, updatedAt, updatedByGuid, deletedAt, deletedByGuid) <> (applyMaybe, unapplyMaybe)

  def applyMaybe[U](t: U) = t match {
    case (
      Some(guid: UUID),
      firstName: String,
      lastName: String,
      email: String,
      password: String,
      avatar: String,
      Some(salt: String),
      createdAt: DateTime,
      createdByGuid: UUID,
      updatedAt: DateTime,
      updatedByGuid: UUID,
      None,
      None) => Option(User(guid, firstName, lastName, email, password, avatar, Some(salt), createdAt, createdByGuid, updatedAt, updatedByGuid, None, None))

    case _ => None
  }

  def unapplyMaybe = (u: Option[User]) => None
}