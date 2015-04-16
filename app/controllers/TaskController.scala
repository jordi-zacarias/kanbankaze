package controllers

import com.inscrum.model.RelBoardColumnTask
import com.inscrum.model.task.Task
import com.inscrum.service.task.TaskService
import com.inscrum.service.user.Oauth2DataHandler
import play.api.mvc.{BodyParsers, Action, Controller}
import play.api.hal._
import play.api.mvc.hal._
import play.api.libs.json._

/**
 * Created by jordi on 03/04/2015.
 */
object TaskController extends Controller {

  import scalaoauth2.provider.OAuth2ProviderActionBuilders._

  def listByBoard(boardId: Int) = Action { implicit req =>
    val tasks = TaskService.getTaskByBoard(boardId)

    //implicit val taskFormat = Json.format[(Task, RelBoardColumnTask)]

    implicit val writer = new Writes[(Task, RelBoardColumnTask)] {
      def writes(c: (Task, RelBoardColumnTask)): JsValue = {
        Json.obj(
          "id" -> c._1.id,
          "title" -> c._1.title,
          "description" -> c._1.description,
          "estimation" -> c._1.estimation,
          "acceptanceCriteria" -> c._1.acceptanceCriteria,
          "blocked" -> c._1.blocked,
          "blockedReason" -> c._1.blockedReason.get,
          "boardColumnId" -> c._2.boardColumnId,
          "position" -> c._2.position
        )
      }
    }

    val jsonTasks = Json.obj("items" -> tasks)

    val resource: HalResource = Hal.state(jsonTasks) ++
      HalLink("self", routes.TaskController.listByBoard(boardId).absoluteURL())

    Ok(resource)
  }

  def listByColumn(columnId: Int) = AuthorizedAction(new Oauth2DataHandler()) { implicit req =>
    val tasks = TaskService.getTaskByColumn(columnId)

    implicit val writer = new Writes[(Task, RelBoardColumnTask)] {
      def writes(c: (Task, RelBoardColumnTask)): JsValue = {
        Json.obj(
          "id" -> c._1.id,
          "title" -> c._1.title,
          "description" -> c._1.description,
          "estimation" -> c._1.estimation,
          "acceptanceCriteria" -> c._1.acceptanceCriteria,
          "blocked" -> c._1.blocked,
          "blockedReason" -> c._1.blockedReason.fold("")(identity),
          "position" -> c._2.position
        )
      }
    }

    val jsonTasks = Json.obj("items" -> tasks)

    val resource: HalResource = Hal.state(jsonTasks) ++
      HalLink("self", routes.TaskController.listByColumn(columnId).absoluteURL())

    Ok(resource)
  }

  def save = Action(BodyParsers.parse.json) { implicit req =>

    implicit val taskReader : Reads[Task] = Json.reads[Task]
    implicit val taskWriter : Writes[Task] = Json.writes[Task]

    val task = req.body.validate[Task]

    val savedTask = TaskService.save(task.get)

    Ok(Json.toJson[Task](savedTask))
  }
}
