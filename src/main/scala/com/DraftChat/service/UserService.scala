package com.DraftChat.service

import com.DraftChat.model.{User, Users}

import scala.concurrent.{Await, Future}
import slick.driver.PostgresDriver.api._

import scala.concurrent.duration.Duration

object UserService {
  private lazy val db = Database.forConfig("mydb")

  def getHash(s: String): String = net.liftweb.util.SecurityHelpers.hash(s)

  def validate(login: String, password: String): Boolean =
    Await.result(
      db.run(
        TableQuery[Users].filter(
          elem => elem.login === login && elem.passHash === getHash(password)
        ).exists.result), Duration.Inf)

  def register(login: String, password: String, name: String): Future[Int] =
    db.run(TableQuery[Users] += User(login, getHash(password), name))
}
