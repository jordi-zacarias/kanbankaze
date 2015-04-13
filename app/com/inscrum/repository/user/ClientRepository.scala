package com.inscrum.repository.user

import com.inscrum.model.user._
import scala.slick.driver.MySQLDriver.simple._


object ClientRepository {

  private val clients = TableQuery[Clients]
  private val clientGrantTypes = TableQuery[ClientGrantTypes]
  private val grantTypes = TableQuery[GrantTypes]

  def validate(id: String, secret: Option[String], grantType: String)(implicit s: Session): Boolean = {
    val check = for {
      ((c, cgt), gt) <- clients innerJoin clientGrantTypes on (_.id === _.clientId) innerJoin grantTypes on (_._2.grantTypeId === _.id)
      if c.id === id && c.secret === secret && gt.grantType === grantType
    } yield c
    check.firstOption.isDefined
  }

  def findById(id: String)(implicit s: Session): Option[Client] = {
    clients.filter(c => c.id === id).firstOption
  }
}
