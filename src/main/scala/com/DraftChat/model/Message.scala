package com.DraftChat.model

import slick.driver.PostgresDriver.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

class Message(tag: Tag) extends Table[(Int, Int, Option[Int], String)](tag, "Message") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def srcUserId: Rep[Int] = column[Int]("srcUserId")

  def dstUserId: Rep[Option[Int]] = column[Int]("dstUserId")

  def message: Rep[String] = column[String]("message")

  def srcUser: ForeignKeyQuery[User, (Int, String, String, String)] =
    foreignKey("srcUser", srcUserId, TableQuery[User])(_.id)

  def dstUser: ForeignKeyQuery[User, (Int, String, String, String)] =
    foreignKey("dstUser", dstUserId, TableQuery[User])(_.id)

  override def * : ProvenShape[(Int, Int, Option[Int], String)] = (id, srcUserId, dstUserId, message)
}
