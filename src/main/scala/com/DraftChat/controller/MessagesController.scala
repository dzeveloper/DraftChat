package com.DraftChat.controller

import java.sql.Timestamp

import com.DraftChat.DraftChatStack
import com.DraftChat.auth.AuthenticationSupport
import com.DraftChat.json.MessageSerializer
import com.DraftChat.model.{Message, User}
import com.DraftChat.service.MessageService
import org.scalatra.Created
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MessagesController extends DraftChatStack with AuthenticationSupport {
  before() {
    requireLogin()
  }

  def getParameterOrBadRequest(name: String): String =
    params.getOrElse(name, halt(400))

  post("/send") {
    val author = request("scentry.auth.default.user").asInstanceOf[User]
    val message = getParameterOrBadRequest("message")
    Await.result(
      MessageService.addMessage(Message(new Timestamp(System.currentTimeMillis()), author.id.get, message)),
      Duration.Inf)
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
