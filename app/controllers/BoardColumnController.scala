package controllers

import com.inscrum.model.BoardColumn
import com.inscrum.service.board.BoardColumnService
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.hal._
import play.api.mvc.hal._
import play.api.libs.json._

/**
 * Created by jordi on 01/04/2015.
 */
object BoardColumnController extends Controller{

  def listByBoard(boardId: Int) = Action { implicit req =>

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

  def get(id: Int) = Action { implicit req =>
    val column = BoardColumnService.getColumn(id)

    implicit val boardWrites: Writes[BoardColumn] = Json.writes[BoardColumn]

    val resource: HalResource = Hal.state(column) ++
      HalLink("self", routes.BoardController.get(id).absoluteURL() ) ++
      HalLink("tasks", routes.TaskController.listByColumn(column.get.id).absoluteURL())

    Ok(resource)
  }
}
