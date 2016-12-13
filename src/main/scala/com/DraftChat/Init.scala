package com.DraftChat

import java.sql.Timestamp

import com.DraftChat.model.{Message, Messages, User, Users}
import com.DraftChat.service.UserService
import slick.driver.PostgresDriver.api._

import scala.concurrent._
import scala.concurrent.duration.Duration

object Init {
  def main(args: Array[String]) {
    val db = Database.forConfig("mydb")
    Await.result(db.run(sqlu"""DROP TABLE IF EXISTS "Message", "User" CASCADE"""), Duration.Inf)
    Await.result(db.run((TableQuery[Users].schema ++ TableQuery[Messages].schema).create), Duration.Inf)

    val users = TableQuery[Users]
    val messages = TableQuery[Messages]

    val fillAction = DBIO.seq(
      users ++= Seq(
        User("admin", UserService.getHash("admin"), "ADMIN"),
        User("user", UserService.getHash("user"), "user")
      ),
      messages ++= Seq(
        Message(new Timestamp(System.currentTimeMillis()), 1, "HELLO EVERYBODY"),
        Message(new Timestamp(System.currentTimeMillis() + 3000), 1, "HELLO user"),
        Message(new Timestamp(System.currentTimeMillis() + 15000), 2, "HELLO admin")
      )
    )
    Await.result(db.run(fillAction), Duration.Inf)
  }
}