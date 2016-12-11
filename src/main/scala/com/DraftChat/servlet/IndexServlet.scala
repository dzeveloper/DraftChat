package com.DraftChat.servlet

import com.DraftChat.DraftChatStack
import com.DraftChat.model._
import slick.driver.PostgresDriver.api._

class IndexServlet extends DraftChatStack {
  get("/login") {
    contentType = "text/html"
    //    jade()
    ssp("/WEB-INF/templates/views/pages/login.ssp", "layout" -> "")
  }

  get("/") {
    redirect("chat")
  }

  get("/chat") {
    val messagesQuery = for {
      mes <- TableQuery[Messages]
      author <- mes.author
    } yield (mes, author)

    contentType = "text/html"
    layoutTemplate("/WEB-INF/templates/layouts/default.ssp")
  }
}
