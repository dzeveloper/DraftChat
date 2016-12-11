package com.DraftChat.servlet

import java.sql.Timestamp

import com.DraftChat.DraftChatStack
import com.DraftChat.json.MessageSerializer
import com.DraftChat.model.{Message, Messages}
import org.scalatra.Created

import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import spray.json.DefaultJsonProtocol._

class MessagesController extends DraftChatStack {
  private lazy val db = Database.forConfig("mydb")

  post("/send") {
    val authorId = params.getOrElse("authorId", halt(400))
    val message = params.getOrElse("message", halt(400))
    Await.result(db.run(TableQuery[Messages] += Message(new Timestamp(System.currentTimeMillis()),
      authorId.toInt, message)), Duration.Inf)
    Created()
  }

  get("/") {
    val messagesQuery = for {
      mes <- TableQuery[Messages]
      author <- mes.author
    } yield (mes, author)

    contentType = "application/json"
    val jsonMessages = for {messages <- db.run(messagesQuery.result)} yield {
      MessageSerializer.serialize(messages).toJson
    }
    Await.result(jsonMessages, Duration.Inf)
  }

  get("/last") {
    val timestamp = new Timestamp(params.getOrElse("timestamp", halt(400)).toLong)
    val messagesQuery = for {
      mes <- TableQuery[Messages].filter(_.timestamp > timestamp)
      author <- mes.author
    } yield (mes, author)

    contentType = "application/json"
    val jsonMessages = for {messages <- db.run(messagesQuery.result)} yield {
      MessageSerializer.serialize(messages).toJson
    }
    Await.result(jsonMessages, Duration.Inf)
  }
}
