package com.DraftChat.servlet

import com.DraftChat.DraftChatStack

class IndexServlet extends DraftChatStack {
  get("/login") {
    contentType = "text/html"
    ssp("/WEB-INF/templates/views/pages/login.ssp", "layout" -> "")
  }

  get("/") {
    redirect("chat")
  }

  get("/chat") {
    contentType = "text/html"
    ssp("/WEB-INF/templates/layouts/default.ssp")
  }
}
