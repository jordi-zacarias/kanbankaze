package controllers

import java.util.UUID

import com.inscrum.model.task.RelTaskUser
import com.inscrum.service.task.TaskService
import com.inscrum.service.user.Oauth2DataHandler
import play.api.mvc.{BodyParsers, Action, Controller}
import play.api.hal._
import play.api.mvc.hal._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by jordi on 20/04/2015.
 */
object TaskUserController extends Controller {

  import scalaoauth2.provider.OAuth2ProviderActionBuilders._

  def save = AuthorizedAction(new Oauth2DataHandler())(BodyParsers.parse.json) { implicit req =>

    implicit val reads : Reads[RelTaskUser] = Json.reads[RelTaskUser]
    implicit val writes : Writes[RelTaskUser] = Json.writes[RelTaskUser]

    val taskUser = req.body.validate[RelTaskUser]

    TaskService.addUser(taskUser.get)

    Ok(Json.toJson[RelTaskUser](taskUser.get))
  }

}
