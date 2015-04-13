package controllers

import com.inscrum.model.Task
import com.inscrum.model.RelBoardColumnTask
import com.inscrum.service.task.TaskService
import com.inscrum.service.user.Oauth2DataHandler
import play.api.mvc.Action
import play.api.mvc.Controller
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
          "position" -> c._2.position
        )
      }
    }

    val jsonTasks = Json.obj("items" -> tasks)

    val resource: HalResource = Hal.state(jsonTasks) ++
      HalLink("self", routes.TaskController.listByColumn(columnId).absoluteURL())

    Ok(resource)
  }
}
