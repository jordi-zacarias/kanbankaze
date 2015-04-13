package com.inscrum.service.user

import com.inscrum.model.user.{AuthCode, AuthCodes}
import com.inscrum.repository.DB
import com.inscrum.repository.user.AuthCodeRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by jordi on 12/04/2015.
 */
object AuthCodeSerivce {

  def find(code: String): Future[Option[AuthCode]] = Future {
    DB { implicit session =>
      AuthCodeRepository.find(code)
    }
  }
}

