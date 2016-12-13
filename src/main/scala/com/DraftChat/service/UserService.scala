package com.DraftChat.service

import com.DraftChat.model.{User, Users}

import scala.concurrent.{Await, Future}
import slick.driver.PostgresDriver.api._
import net.liftweb.util.SecurityHelpers._

import scala.concurrent.duration.Duration

object UserService {
  private lazy val db = Database.forConfig("mydb")

  def getUser(login: String): Option[User] =
    Await.result(db.run(TableQuery[Users].filter(_.login === login).result.headOption), Duration.Inf)

  def getUser(login: String, password: String): Option[User] =
    Await.result(
      db.run(
        TableQuery[Users].filter(
          elem => elem.login === login && elem.passHash === hash(password)
        ).result.headOption), Duration.Inf)

  def register(login: String, password: String, name: String): Future[Int] =
    db.run(TableQuery[Users] += User(login, hash(password), name))
}
