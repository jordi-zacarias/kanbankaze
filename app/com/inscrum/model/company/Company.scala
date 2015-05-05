package com.inscrum.model.company

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by jordi on 29/04/2015.
 */

case class Company (id: Int, name: String, description: String)

class CompanyTable (tag: Tag) extends Table[Company](tag, "COMPANY") {
  def id: Column[Int] = column[Int]("comppany_id", O.PrimaryKey, O.AutoInc)
  def name: Column[String] = column[String]("name")
  def description: Column[String] = column[String]("description")

  def * = (id, name, description) <> (Company.tupled, Company.unapply _)
}
