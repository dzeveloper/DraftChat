package com.DraftChat.json

import java.sql.Timestamp

import spray.json.{JsNumber, _}
import DefaultJsonProtocol._
import com.DraftChat.model.{Message, Messages, Users}

object MessageSerializer {

  implicit object TimestampFormat extends RootJsonFormat[Timestamp] {
    override def write(obj: Timestamp) = JsNumber(obj.getTime)

    override def read(json: JsValue): Timestamp = json match {
      case JsNumber(n) => new Timestamp(n.toLong)
      case _ => deserializationError("Timestamp expected")
    }
  }

  def serialize(messages: Seq[(Messages#TableElementType, Users#TableElementType)]): Seq[JsObject] = {
    val jsElems = messages.map(elem =>
      JsObject(Map("message" -> JsString(elem._1.message),
        "author" -> JsString(elem._2.name),
        "login" -> JsString(elem._2.login),
        "timestamp" -> JsNumber(elem._1.timestamp.getTime))))
    jsElems
  }
}
