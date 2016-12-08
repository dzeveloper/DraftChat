package com.DraftChat.model

import slick.driver.PostgresDriver.api._
import slick.lifted.{Index, ProvenShape}

case class User(login: String, passHash: String, name: String, id: Option[Int] = None)

class Users(tag: Tag) extends Table[User](tag, "User") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def login: Rep[String] = column[String]("login")

  def passHash: Rep[String] = column[String]("passHash")

  def name: Rep[String] = column[String]("name")

  def idxLogin: Index = index("idxLogin", login, unique = true)

  def * : ProvenShape[User] = (login, passHash, name, id.?) <> (User.tupled, User.unapply)
}
