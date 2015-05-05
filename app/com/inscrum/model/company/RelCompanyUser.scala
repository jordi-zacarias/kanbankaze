package com.inscrum.model.company

import java.util.UUID
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by jordi on 29/04/2015.
 */
case class RelCompanyUser(companyId: Int, userGuid: UUID)

class RelCompanyUserTable (tag: Tag) extends Table[RelCompanyUser](tag, "COMPANY_USER") {

  def companyId: Column[Int] = column[Int]("company_id")
  def userGuid: Column[UUID] = column[UUID]("user_guid")

  def * = (companyId, userGuid) <> (RelCompanyUser.tupled, RelCompanyUser.unapply _)
}

