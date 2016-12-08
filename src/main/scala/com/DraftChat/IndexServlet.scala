package com.DraftChat

import java.sql.Timestamp

import com.DraftChat.gson.IdSerializer
import com.DraftChat.model._
import com.google.gson.GsonBuilder
import org.scalatra.Created
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent._
import scala.concurrent.duration.Duration


class IndexServlet extends DraftChatStack {

  private val db = Database.forConfig("mydb")

  get("/") {
    redirect("chat")
  }

  get("/chat") {
    val messagesQuery = for {
      mes <- TableQuery[Messages]
      author <- mes.author
    } yield (mes, author)

    val messages = Await.result(db.run(messagesQuery.result), Duration.Inf)

    contentType = "text/html"
    ssp("/pages/index.ssp", "messages" -> messages)
  }

  post("/sendMessage") {
    val authorId = params("authorId")
    val message = params("message")
    val timestampS = params("timestamp")

    Await.result(db.run(TableQuery[Messages] += Message(new Timestamp(timestampS.toLong), authorId.toInt, message)),
      Duration.Inf)
    Created()
  }

  get("/messages") {
    contentType = "application/json"
    val jsonMessages = for {messages <- db.run(TableQuery[Messages].result)} yield {
      val gsonBuilder = new GsonBuilder
      gsonBuilder.registerTypeAdapter(classOf[Option[Int]], new IdSerializer)
      gsonBuilder.setPrettyPrinting().create.toJson(
        scala.collection.JavaConversions.seqAsJavaList(messages)
      )
    }
    Await.result(jsonMessages, Duration.Inf)
  }
}
