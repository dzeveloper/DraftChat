package com.DraftChat.controller

import com.DraftChat.DraftChatStack

class IndexController extends DraftChatStack {
  get("/") {
    redirect("/chat")
  }
}
