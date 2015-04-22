package controllers

import com.inscrum.service.user.UserService
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

  def list() = Action { implicit req =>

    val users = UserService.getAll()

    //implicit val userFormat = Json.format[User]
    implicit val userWrites = new Writes[User] {
      def writes(u: User): JsValue = {
        Json.obj(
          "id" -> u.guid,
          "firstName" -> u.firstName,
          "lastName" -> u.lastName,
          "email" -> u.email
        )
      }
    }
    val jsonUsers = Json.obj("items" -> users)

    val resource: HalResource = Hal.state(jsonUsers) ++
      HalLink("self", routes.UserController.list().absoluteURL() )

    Ok(resource)
  }

}
