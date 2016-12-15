package com.DraftChat.controller

import com.DraftChat.DraftChatStack
import com.DraftChat.auth.AuthenticationSupport
import com.DraftChat.service.UserService

class AuthenticationController extends DraftChatStack with AuthenticationSupport {

  get(".*".r) {
    contentType = "text/html"
    if (!isAuthenticated)
      ssp("/WEB-INF/templates/views/pages/login.ssp", "layout" -> "", "errorMessage" -> "")
    else
      redirect("/chat")
  }

  post("/") {
    scentry.authenticate()
    redirect("/chat")
  }

  post("/logout") {
    scentry.logout()
    redirect("/")
  }

  post("/register") {
    def getParameterOrSendError(name: String): String =
      params.getOrElse(name,
        halt(400,
          ssp("/WEB-INF/templates/views/pages/login.ssp",
            "layout" -> "", "errorMessage" -> s"Cannot register. Check your $name")
        ))

    UserService.register(getParameterOrSendError("login"),
      getParameterOrSendError("password"), getParameterOrSendError("name"))
    scentry.authenticate()
    redirect("/")
  }
}
