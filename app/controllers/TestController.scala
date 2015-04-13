package controllers

import com.inscrum.repository.user.UserRepository
import play.api.mvc._
import java.util.UUID
import com.inscrum.model.user.User
import org.joda.time.DateTime
import org.mindrot.jbcrypt.BCrypt
import com.inscrum.repository.DB


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
    val u = User(java.util.UUID.randomUUID, "test", "test", "t@t.com", hash, Some(salt), now, uuid, now, uuid, None, None)
      UserRepository.createUser(u)
    }

    Ok("tot ok")
  }

}
