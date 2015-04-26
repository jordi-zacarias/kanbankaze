package controllers

import com.inscrum.repository.user.UserRepository
import com.inscrum.service.task.TaskService
import play.api.mvc._
import java.util.UUID
import com.inscrum.model.user._
import org.joda.time.DateTime
import org.mindrot.jbcrypt.BCrypt
import com.inscrum.repository.DB
import play.api.libs.json._
import com.inscrum.model.task._


/**
 * Created by jordi on 12/04/2015.
 */
object TestController  extends Controller {

  def createUser() = Action {
    DB { implicit session =>
      val salt = BCrypt.gensalt(10)
      val hash = BCrypt.hashpw("demo", salt)
      val now = DateTime.now
      val uuid = UUID.randomUUID
      val u = User(java.util.UUID.randomUUID, "Product", "Owner", "product@kanbankaze.com", hash, "profile/productOwner_128x128.png", Some(salt), now, uuid, now, uuid, None, None)
      UserRepository.createUser(u)
    }

    Ok("tot ok")
  }

  def testQuery() = Action {
    val tasks2 = TaskService.getTaskByColumn(1)

    implicit val userWrites = Json.writes[UserSimple]

    implicit val taskWrites = new Writes[Task]  {
      def writes(t: Task): JsValue = {
        Json.obj(
          "id" -> t.id,
          "title" -> t.title,
          "users" -> t.users
        )
      }
    }

    //implicit def tasUserTuple[Task : Writes, User : Writes] = Writes[(Task, User)] ( o =>  Json.arr(o._1, o._2) )

    Ok(Json.toJson(tasks2))
  }

}
