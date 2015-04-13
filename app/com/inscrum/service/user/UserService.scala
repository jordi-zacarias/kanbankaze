package com.inscrum.service.user

import java.util.UUID

import com.inscrum.model.user.{ConfirmationToken, User}
import com.inscrum.repository.DB
import com.inscrum.repository.user.UserRepository

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by jordi on 12/04/2015.
 */
object UserService {

  def getUserByEmail(email: String): Future[Try[Option[User]]] = Future {
    Try {
      DB { implicit session =>
        UserRepository.getUserByEmail(email)
      }
    }.recover {
      case e =>
        //Logger.error(s"Unable to get a user by email '$email': ${e.getMessage}")
        sys.error(s"Unable to get a user by email '$email': ${e.getMessage}")
    }
  }

  def getByGuid(guid: UUID): Future[Try[Option[User]]] = Future {
    Try {
      DB { implicit session =>
        UserRepository.getByGuid(guid)
      }
    }.recover {
      case e =>
        //Logger.error(s"Unable to get a user by guid '$guid': ${e.getMessage}")
        sys.error(s"Unable to get a user by guid '$guid': ${e.getMessage}")
    }
  }

  def createUser(user: User): Future[Try[Option[User]]] = Future {
    Try {
      DB { implicit session =>
        UserRepository.createUser(user)
      }
    }.recover {
      case e =>
        //Logger.error(s"Unable to create user '${user.email}': ${e.getMessage}")
        sys.error(s"Unable to create user '${user.email}': ${e.getMessage}")
    }
  }

  def createConfirmationToken(confirmationToken: ConfirmationToken): Future[Try[Option[ConfirmationToken]]] = Future {
    Try {
      DB { implicit session =>
        UserRepository.createConfirmationToken(confirmationToken)
      }
    }.recover {
      case e =>
        //Logger.error(s"Unable create confirmation token for user '${confirmationToken.email}': ${e.getMessage}")
        sys.error(s"Unable create confirmation token for user '${confirmationToken.email}': ${e.getMessage}")
    }
  }

  def deleteConfirmationToken(uuid: UUID): Future[Try[Option[ConfirmationToken]]] = Future {
    Try {
      DB { implicit session =>
        UserRepository.deleteConfirmationToken(uuid)
      }
    }.recover {
      case e =>
        //Logger.error(s"Unable delete confirmation token '$uuid': ${e.getMessage}")
        sys.error(s"Unable delete confirmation token '$uuid': ${e.getMessage}")
    }
  }

  def getConfirmationTokenByUUID(uuid: UUID): Future[Try[Option[ConfirmationToken]]] = Future {
    Try {
      DB { implicit session =>
        UserRepository.getConfirmationTokenByUUID(uuid)
      }
    }.recover {
      case e =>
        //Logger.error(s"Unable to get token for uuid $uuid, ${e.getMessage}")
        sys.error(s"Unable to get token for uuid $uuid, ${e.getMessage}")
    }
  }

}
