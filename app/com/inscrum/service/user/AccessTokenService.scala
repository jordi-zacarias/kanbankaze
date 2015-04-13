package com.inscrum.service.user

import java.util.UUID

import com.inscrum.model.user.AccessToken
import com.inscrum.repository.DB
import com.inscrum.repository.user.AccessTokenRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by jordi on 12/04/2015.
 */
object AccessTokenService{

  def create(accessToken: AccessToken): Future[Unit] = Future {
    DB { implicit session =>
      AccessTokenRepository.create(accessToken)
    }
  }

  def deleteExistingAndCreate(accessToken: AccessToken,
                              userGuid: UUID,
                              clientId: Option[String]): Future[Unit] = Future {
    // these two operations should happen inside a transaction
    DB { implicit session =>
      AccessTokenRepository.deleteExistingAndCreate(accessToken, userGuid, clientId)
    }
  }

  def findToken(userGuid: UUID, clientId: Option[String]): Future[Option[AccessToken]] = Future {
    DB { implicit session =>
      AccessTokenRepository.findToken(userGuid, clientId)
    }
  }

  def findAccessToken(token: String): Future[Option[AccessToken]] = Future {
    DB { implicit session =>
      AccessTokenRepository.findAccessToken(token)
    }
  }

  def findRefreshToken(token: String): Future[Option[AccessToken]] = Future {
    DB { implicit session =>
      AccessTokenRepository.findRefreshToken(token)
    }
  }
}

