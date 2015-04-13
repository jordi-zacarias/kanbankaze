package com.inscrum.repository.user

import java.util.UUID

import com.inscrum.model.user._
import scala.slick.driver.MySQLDriver.simple._

import scala.slick.lifted.TableQuery

object AccessTokenRepository {

  private val accessTokens = TableQuery[AccessTokens]

  def create(accessToken: AccessToken)(implicit s: Session) {
      accessTokens.insert(accessToken)
  }

  def deleteExistingAndCreate(accessToken: AccessToken,
                                       userGuid: UUID,
                                       clientId: Option[String])(implicit s: Session) = {
    // these two operations should happen inside a transaction
    accessTokens.filter(a => a.clientId === clientId && a.userGuid === userGuid).delete
    accessTokens.insert(accessToken)
  }

  def findToken(userGuid: UUID, clientId: Option[String])
                        (implicit s: Session): Option[AccessToken] = {
    accessTokens.filter(a => a.clientId === clientId && a.userGuid === userGuid).firstOption
  }

  def findAccessToken(token: String)(implicit s: Session): Option[AccessToken] = {
    accessTokens.filter(a => a.accessToken === token).firstOption
  }

  def findRefreshToken(token: String)(implicit s: Session): Option[AccessToken] = {
    accessTokens.filter(a => a.refreshToken === token).firstOption
  }
}
