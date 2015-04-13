package controllers

import com.inscrum.service.board.BoardService
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.hal._
import play.api.mvc.hal._
import com.inscrum.model.Board
import play.api.libs.json._



object BoardController extends Controller {

  def list = Action{ implicit req =>
    
    val boards = BoardService.getAll()

    //implicit val boardWrites: Writes[Board] = Json.writes[Board]
    implicit val boardFormat = Json.format[Board]
    val jsonBoards = Json.obj("items" -> boards)

    val resource: HalResource = Hal.state(jsonBoards) ++
      HalLink("self", routes.BoardController.list().absoluteURL() )

    Ok(resource)
  }
  
  def get(id: Int) = Action { implicit req =>
    
    val board = BoardService.getBoard(id)
    
    implicit val boardWrites: Writes[Board] = Json.writes[Board]
    
    val resource: HalResource = Hal.state(board) ++
                                HalLink("self", routes.BoardController.get(id).absoluteURL()) ++
                                HalLink("columns", routes.BoardColumnController.listByBoard(id).absoluteURL())
      
    Ok(resource)
    
  }
    
}