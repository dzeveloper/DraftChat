package com.DraftChat.auth.strategies

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.DraftChat.model.{User, Users}
import org.scalatra.ScalatraBase
import org.scalatra.auth.ScentryStrategy
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class CredentialsStrategy(protected val app: ScalatraBase)
                         (implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[User] {

  private lazy val db = Database.forConfig("mydb")

  def getHash(s: String): String = net.liftweb.util.SecurityHelpers.hash(s)

  private def login = app.params.getOrElse("login", "")

  private def password = app.params.getOrElse("password", "")

  override def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
    Await.result(
      db.run(
        TableQuery[Users].filter(
          elem => elem.login === login && elem.passHash === getHash(password)
        ).result.headOption), Duration.Inf)
  }
}
