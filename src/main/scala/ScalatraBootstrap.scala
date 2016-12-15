import javax.servlet.ServletContext

import com.DraftChat.controller.{AuthenticationController, ChatController, IndexController, MessagesController}
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new IndexController, "/")
    context.mount(new ChatController, "/chat")
    context.mount(new MessagesController, "/message")
    context.mount(new AuthenticationController, "/auth")
  }
}
