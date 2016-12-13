package com.DraftChat.auth

import com.DraftChat.auth.strategies.CredentialsStrategy
import com.DraftChat.model.User
import com.DraftChat.service.UserService
import org.scalatra.ScalatraBase
import org.scalatra.auth.{ScentryConfig, ScentrySupport}
import org.slf4j.LoggerFactory

trait AuthenticationSupport extends ScalatraBase with ScentrySupport[User] {
  self: ScalatraBase =>

  protected def fromSession: PartialFunction[String, User] = {
    case login: String => UserService.getUser(login).get
  }

  protected def toSession: PartialFunction[User, String] = {
    case usr: User => usr.login
  }

  protected val scentryConfig: ScentryConfiguration = new ScentryConfig {
    override val login = "/auth"
  }.asInstanceOf[ScentryConfiguration]

  protected def requireLogin(): Unit = {
    if (!isAuthenticated) {
      redirect(scentryConfig.login)
    }
  }

  override protected def configureScentry(): Unit = {
    scentry.unauthenticated {
      response.sendRedirect(scentryConfig.login)
    }
  }

  override protected def registerAuthStrategies(): Unit = {
    scentry.register("CredentialsStrategy", app => new CredentialsStrategy(app))
  }
}
