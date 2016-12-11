import org.scalatra._
import javax.servlet.ServletContext

import com.DraftChat.servlet.{IndexServlet, MessagesController}

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new IndexServlet, "/*")
    context.mount(new MessagesController, "/message/*")
  }
}
