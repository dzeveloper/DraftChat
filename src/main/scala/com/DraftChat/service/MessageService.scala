package com.DraftChat.service

import java.sql.Timestamp

import com.DraftChat.model.{Message, Messages, User}
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future

object MessageService {
  private val db = Database.forConfig("mydb")

  def getMessagesWithAuthor: Future[Seq[(Message, User)]] = {
    val messagesQuery = for {
      message <- TableQuery[Messages]
      author <- message.author
    } yield (message, author)

    db.run(messagesQuery.result)
  }

  def getLastMessagesWithAuthor(timestamp: Timestamp): Future[Seq[(Message, User)]] = {
    val messagesQuery = for {
      message <- TableQuery[Messages].filter(_.timestamp > timestamp)
      author <- message.author
    } yield (message, author)

    db.run(messagesQuery.result)
  }
}
