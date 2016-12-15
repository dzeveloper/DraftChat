package com.DraftChat.controller

import com.DraftChat.DraftChatStack
import com.DraftChat.auth.AuthenticationSupport
import com.DraftChat.model.User

class ChatController extends DraftChatStack with AuthenticationSupport {

  before() {
    requireLogin()
  }

  get("/") {
    contentType = "text/html"
    ssp("/WEB-INF/templates/layouts/default.ssp", "name" -> request("scentry.auth.default.user").asInstanceOf[User].name)
  }
}
