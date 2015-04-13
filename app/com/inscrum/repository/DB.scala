package com.inscrum.repository

import scala.slick.driver.MySQLDriver.simple._
import play.api.Play.current


/**
 * Created by jordi on 29/03/2015.
 */
object DB {

  lazy private val default = Database.forDataSource(play.api.db.DB.getDataSource("dev"))
  /**
   * Run the supplied function with a new session and automatically close the session at the end.
   */
  def apply[T](f: Session => T): T = default.withSession(f)

}
