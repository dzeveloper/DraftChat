package com.DraftChat.servlet

import java.sql.Timestamp
import javax.servlet.http.HttpServletRequest

import com.DraftChat.DraftChatStack
import com.DraftChat.json.MessageSerializer
import com.DraftChat.model.{Message, Messages}
import com.DraftChat.service.MessageService
import org.scalatra.Created
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import spray.json.DefaultJsonProtocol._

class MessagesController extends DraftChatStack {
  private lazy val db = Database.forConfig("mydb")

  def getParameterOrBadRequest(name: String): String =
    params.getOrElse(name, halt(400))

  post("/send") {
    val authorId = getParameterOrBadRequest("authorId")
    val message = getParameterOrBadRequest("message")
    Await.result(db.run(TableQuery[Messages] += Message(new Timestamp(System.currentTimeMillis()),
      authorId.toInt, message)), Duration.Inf)
    Created()
  }

  get("/") {
    contentType = "application/json"
    MessageSerializer.serialize(Await.result(MessageService.getMessagesWithAuthor, Duration.Inf)).toJson
  }

  get("/last") {
    val timestamp = new Timestamp(getParameterOrBadRequest("timestamp").toLong)

    contentType = "application/json"
    MessageSerializer.serialize(Await.result(MessageService.getLastMessagesWithAuthor(timestamp), Duration.Inf)).toJson
  }
}
