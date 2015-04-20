package controllers

import com.inscrum.model.task.RelTaskUser
import com.inscrum.service.task.TaskService
import play.api.mvc.{BodyParsers, Action, Controller}
import play.api.hal._
import play.api.mvc.hal._
import play.api.libs.json._


/**
 * Created by jordi on 20/04/2015.
 */
object TaskUserController extends Controller {

  def save = Action(BodyParsers.parse.json) { implicit req =>

    implicit val taskReader : Reads[RelTaskUser] = Json.reads[RelTaskUser]
    implicit val taskWriter : Writes[RelTaskUser] = Json.writes[RelTaskUser]

    val taskUser = req.body.validate[RelTaskUser]

    TaskService.addUser(taskUser.get)

    Ok(Json.toJson[RelTaskUser](taskUser.get))
  }

}
