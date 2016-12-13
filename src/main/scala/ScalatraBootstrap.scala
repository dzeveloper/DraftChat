import org.scalatra._
import javax.servlet.ServletContext

import com.DraftChat.servlet.{AuthenticationController, ChatServlet, MessagesController}

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new ChatServlet, "/*")
    context.mount(new MessagesController, "/message/*")
    context.mount(new AuthenticationController, "/auth/*")
  }
}
