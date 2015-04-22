package controllers

import com.inscrum.model.{RelBoardColumnTask, BoardColumn}
import com.inscrum.service.board.{RelBoardColumnTaskService, BoardColumnService}
import com.inscrum.service.user.Oauth2DataHandler
import play.api.mvc.{BodyParsers, Action, Controller}
import play.api.hal._
import play.api.mvc.hal._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by jordi on 01/04/2015.
 */
object BoardColumnController extends Controller{

  import scalaoauth2.provider.OAuth2ProviderActionBuilders._

  implicit val relBoardColumnTaskReads: Reads[RelBoardColumnTask] = (
    (JsPath \ "boardColumnId").read[Int] and
      (JsPath \ "taskId").read[Int] and
      (JsPath \ "position").read[Int]
  )(RelBoardColumnTask.apply _)

  def listByBoard(boardId: Int) = AuthorizedAction(new Oauth2DataHandler()) { implicit req =>

    val columns = BoardColumnService.getColumnsByBoard(boardId)
    //implicit val boardFormat = Json.format[BoardColumn]

    implicit val writer = new Writes[BoardColumn] {
      def writes(c: BoardColumn): JsValue = {
        Json.obj(
          "id" -> c.id,
          "boardId" -> c.boardId,
          "title" -> c.title,
          "finalStatus" -> c.finalStatus,
          "position" -> c.position,
          "_links" -> Json.obj(
            "self" -> routes.BoardController.get(c.id).absoluteURL(),
            "tasks" -> routes.TaskController.listByColumn(c.id).absoluteURL()
          )
        )
      }
    }

    val jsonColumns = Json.obj("items" -> columns)

    val resource: HalResource = Hal.state(jsonColumns) ++
      HalLink("self", routes.BoardController.list().absoluteURL() )

    Ok(resource)
  }

  def get(id: Int) = AuthorizedAction(new Oauth2DataHandler()) { implicit req =>
    val column = BoardColumnService.getColumn(id)

    implicit val boardWrites: Writes[BoardColumn] = Json.writes[BoardColumn]

    val resource: HalResource = Hal.state(column) ++
      HalLink("self", routes.BoardController.get(id).absoluteURL() ) ++
      HalLink("tasks", routes.TaskController.listByColumn(column.get.id).absoluteURL())

    Ok(resource)
  }

  def insertTasks = AuthorizedAction(new Oauth2DataHandler())(BodyParsers.parse.json) { implicit req =>

    val columnTask = req.body.validate[RelBoardColumnTask]
    RelBoardColumnTaskService.save(columnTask.get)

    Ok("inserted")
  }

  def updateTasks = AuthorizedAction(new Oauth2DataHandler())(BodyParsers.parse.json) { implicit req =>

    val columnTasks = req.body.validate[List[RelBoardColumnTask]]

    columnTasks.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(errors)))
      },
      list => {
        list.foreach(
          RelBoardColumnTaskService.update(_)
        )
        Ok("" + list.size)
      }
    )
  }
}
