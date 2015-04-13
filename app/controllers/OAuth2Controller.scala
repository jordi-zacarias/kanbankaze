package controllers


import com.inscrum.secure.InscrumTokenEndpoint
import play.api.mvc.Action
import play.api.mvc.Controller
import com.inscrum.service.user.Oauth2DataHandler
import scala.concurrent.ExecutionContext.Implicits.global

import scalaoauth2.provider.{TokenEndpoint, OAuth2Provider}

/**
 * Created by jordi on 12/04/2015.
 */

trait MyOAuth extends OAuth2Provider {
  override val tokenEndpoint: TokenEndpoint = InscrumTokenEndpoint
}

object OAuth2Controller extends Controller with MyOAuth {
  def accessToken = Action.async { implicit request =>
    issueAccessToken(new Oauth2DataHandler())
  }
}
