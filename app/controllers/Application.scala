package controllers

import play.api.mvc._

/**
 * Created by jordi on 01/04/2015.
 */
object Application extends Controller {

  def index = Action { implicit rs =>
    Ok(views.html.index())
  }
}
