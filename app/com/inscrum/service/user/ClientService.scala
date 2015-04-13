package com.inscrum.service.user

import com.inscrum.model.user.Client
import com.inscrum.repository.DB
import com.inscrum.repository.user.ClientRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by jordi on 12/04/2015.
 */
object ClientService {

  def validate(id: String, secret: Option[String], grantType: String): Future[Boolean] = Future {
    DB { implicit session =>
      ClientRepository.validate(id, secret, grantType)
    }
  }

  def findById(id: String): Future[Option[Client]] = Future {
    DB { implicit session =>
      ClientRepository.findById(id)
    }
  }
}
