package com.DraftChat.gson

import java.lang.reflect.Type

import com.google.gson._

class IdSerializer extends JsonSerializer[Option[Int]] {
  def serialize(src: Option[Int], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    src match {
      case Some(v: Int) => new JsonPrimitive(v)
      case None => null
    }
  }
}