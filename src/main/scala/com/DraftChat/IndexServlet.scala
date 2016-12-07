package com.DraftChat


class IndexServlet extends DraftChatStack {

  get("/") {
    <html>
      <body>
        <h1>Welcome to DraftChat!</h1>
        <h2>Sending you to chat!</h2>
      </body>
    </html>
    redirect("chat")
  }

  get("/chat") {
    contentType = "text/html"
    ssp("/pages/index.ssp", "title" -> "DraftChat")
  }

  get("/users") {
    contentType = "text/html"

  }

}
