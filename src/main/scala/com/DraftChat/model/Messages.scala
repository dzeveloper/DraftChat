package com.DraftChat.model

import java.sql.Timestamp

import slick.driver.PostgresDriver.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

case class Message(timestamp: Timestamp, authorId: Int, message: String, id: Option[Int] = None)

class Messages(tag: Tag) extends Table[Message](tag, "Message") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def timestamp: Rep[Timestamp] = column[Timestamp]("timestamp")

  def authorId: Rep[Int] = column[Int]("authorId")

  def message: Rep[String] = column[String]("message")

  def author: ForeignKeyQuery[Users, User] =
    foreignKey("author", authorId, TableQuery[Users])(_.id)

  def * : ProvenShape[Message] = (timestamp, authorId, message, id.?) <> (Message.tupled, Message.unapply)
}