package controllers

import com.inscrum.service.user.{Oauth2DataHandler, UserService}
import com.inscrum.model.user._
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.hal._
import play.api.mvc.hal._
import play.api.libs.json._

/**
 * Created by jordi on 21/04/2015.
 */
object UserController extends Controller {

  import scalaoauth2.provider.OAuth2ProviderActionBuilders._

  //def list() = AuthorizedAction(new Oauth2DataHandler()) { implicit req =>
  def list() = Action { implicit req =>

    val users = UserService.getAll()

    implicit val userWrites = new Writes[User] {
      def writes(u: User): JsValue = {
        Json.obj(
          "id" -> u.guid,
          "firstName" -> u.firstName,
          "lastName" -> u.lastName,
          "email" -> u.email,
          "avatar" -> u.avatar
        )
      }
    }
    val jsonUsers = Json.obj("items" -> users)

    val resource: HalResource = Hal.state(jsonUsers) ++
      HalLink("self", routes.UserController.list().absoluteURL() )

    Ok(resource)
  }

}
