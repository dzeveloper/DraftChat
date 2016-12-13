package com.DraftChat.servlet

import com.DraftChat.DraftChatStack
import com.DraftChat.auth.AuthenticationSupport

class ChatServlet extends DraftChatStack with AuthenticationSupport {

  before() {
    requireLogin()
  }

  get("/") {
    redirect("chat")
  }

  get("/chat") {
    contentType = "text/html"
    ssp("/WEB-INF/templates/layouts/default.ssp")
  }
}
