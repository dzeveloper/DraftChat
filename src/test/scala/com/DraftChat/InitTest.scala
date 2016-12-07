package com.DraftChat

import com.DraftChat.model.{Message, User}
import org.scalatest.FunSuite
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class InitTest extends FunSuite {
  test("init schema and fill some data") {
    val db = Database.forConfig("mydb")
    Await.result(db.run(sqlu"""DROP TABLE IF EXISTS "Message", "User" CASCADE"""), Duration.Inf)
    Await.result(db.run((TableQuery[User].schema ++ TableQuery[Message].schema).create), Duration.Inf)

    val users = TableQuery[User]
    val messages = TableQuery[Message]

    val fillAction = DBIO.seq(
      users ++= Seq(
        (0, "admin", "what is hash", "ADMIN"),
        (0, "user", "baby don't hurt me", "user")
      ),
      messages ++= Seq(
        (0, 1, None, "HELLO EVERYBODY"),
        (0, 1, Option(2), "HELLO user"),
        (0, 2, Option(1), "HELLO admin")
      )
    )
    Await.result(db.run(fillAction), Duration.Inf)
  }
}