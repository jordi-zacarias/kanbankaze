package com.inscrum.repository.user

import java.util.UUID

import com.inscrum.model.user._
import scala.slick.driver.MySQLDriver.simple._

object UserRepository {

  private val users = TableQuery[Users]
  private val confirmationTokens = TableQuery[ConfirmationTokens]

  def getUserByEmail(email: String)(implicit s: Session): Option[User] = {
      users.filter(u => u.email === email).firstOption
  }

  def getByGuid(guid: UUID)(implicit s: Session): Option[User] = {

      users.filter(u => u.guid === guid).firstOption
  }

  def createUser(user: User)(implicit s: Session): Option[User] = {
      val guid = users += user
      users.filter(u => u.guid === user.guid).firstOption
  }

  def createConfirmationToken(confirmationToken: ConfirmationToken)(implicit s: Session): Option[ConfirmationToken] = {
      val id = (confirmationTokens returning confirmationTokens.map(_.id)) += confirmationToken

      Some(confirmationToken.copy(id = Some(id)))
  }

  def deleteConfirmationToken(uuid: UUID)(implicit s: Session): Option[ConfirmationToken] = {

      val tokenResults = confirmationTokens.filter(_.uuid === uuid)
      tokenResults.list match {
        case List() => None
        case List(t) =>
          tokenResults.delete
          Some(ConfirmationToken(t.id, t.uuid, t.email, t.creationTime, t.expirationTime, t.isSignUp))

        case l =>
          //Logger.error(s"Should have found only one token with uuid $uuid, but found $l")
          sys.error(s"Should have found only one token with uuid $uuid, but found $l")
      }
  }

  def getConfirmationTokenByUUID(uuid: UUID)(implicit s: Session): Option[ConfirmationToken] = {

      confirmationTokens.filter(_.uuid === uuid).list match {
        case List() => None
        case List(t) => Some(ConfirmationToken(t.id, t.uuid, t.email, t.creationTime, t.expirationTime, t.isSignUp))
        case l =>
          //Logger.error(s"Should have found only one token with uuid $uuid, but found $l")
          sys.error(s"Should have found only one token with uuid $uuid, but found $l")
      }

  }

  def getAll()(implicit s: Session) : List[User] = users.list
}
