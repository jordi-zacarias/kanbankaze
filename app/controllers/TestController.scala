package controllers

import com.inscrum.repository.user.UserRepository
import com.inscrum.service.task.TaskService
import play.api.mvc._
import java.util.UUID
import com.inscrum.model.user.User
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
      val hash = BCrypt.hashpw("t", salt)
      val now = DateTime.now
      val uuid = UUID.randomUUID
      val u = User(java.util.UUID.randomUUID, "test", "test", "t@t.com", hash, "", Some(salt), now, uuid, now, uuid, None, None)
      UserRepository.createUser(u)
    }

    Ok("tot ok")
  }

  def testQuery() = Action {
    val tasks2 = TaskService.getTaskByColumn2(1)

    implicit val taskWrites = Json.writes[Task]

    implicit val userWrites = new Writes[User] {
      def writes(u: User): JsValue = {
        Json.obj(
          "id" -> u.guid,
          "firstName" -> u.firstName,
          "lastName" -> u.lastName,
          "email" -> u.email,
          "avatar" -> u.avatar
        )
      }
    }

    implicit def tasUserTuple[Task : Writes, User : Writes] = Writes[(Task, User)] ( o =>  Json.arr(o._1, o._2) )

    Ok(Json.toJson(tasks2))
  }

}
