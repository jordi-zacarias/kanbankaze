package com.inscrum.repository.user

import com.inscrum.model.user._
import org.joda.time.DateTime
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.lifted.TableQuery


import scala.concurrent.{Future, ExecutionContext}

object AuthCodeRepository {

  private val authCodes = TableQuery[AuthCodes]

  def find(code: String)(implicit s: Session): Option[AuthCode] = {
    val authCode = authCodes.filter(a => a.authorizationCode === code).firstOption

    // filtering out expired authorization codes
    authCode.filter(p => p.createdAt.getMillis + (p.expiresIn * 1000) > DateTime.now.getMillis)
  }
}
