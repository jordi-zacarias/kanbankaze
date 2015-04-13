package com.inscrum.secure

import scalaoauth2.provider.TokenEndpoint
import scalaoauth2.provider.Password

/**
 * Created by jordi on 12/04/2015.
 */
class InscrumTokenEndpoint extends TokenEndpoint {
  val passwordNoCred = new Password() {
    override def clientCredentialRequired = false
  }

  override val handlers = Map(
    "password" -> passwordNoCred
  )
}

object InscrumTokenEndpoint extends InscrumTokenEndpoint
