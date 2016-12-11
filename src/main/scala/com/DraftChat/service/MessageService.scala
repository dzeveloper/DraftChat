package com.DraftChat.service

import com.DraftChat.model.{Message, Messages, User}
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MessageService {
  private val db = Database.forConfig("mydb")

  def getMessagesWithAuthor: Seq[(Message, User)] = {
    val messagesQuery = for {
      message <- TableQuery[Messages]
      author <- message.author
    } yield (message, author)

    Await.result(db.run(messagesQuery.result), Duration.Inf)
  }
}
