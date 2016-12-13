package com.DraftChat.servlet

import com.DraftChat.DraftChatStack
import com.DraftChat.auth.AuthenticationSupport

import scala.collection.mutable

class AuthenticationController extends DraftChatStack with AuthenticationSupport {

  val tokens: mutable.Map[String, String] = scala.collection.mutable.Map()

  get("/") {
    contentType = "text/html"
    ssp("/WEB-INF/templates/views/pages/login.ssp", "layout" -> "", "errorMessage" -> "")
  }

  post("/") {
    scentry.authenticate()
    redirect("/")
  }
}
