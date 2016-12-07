package com.DraftChat.model

import slick.lifted.{Index, ProvenShape}
import slick.driver.PostgresDriver.api._

class User(tag: Tag) extends Table[(Int, String, String, String)](tag, "User") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def login: Rep[String] = column[String]("login")

  def passHash: Rep[String] = column[String]("passHash")

  def name: Rep[String] = column[String]("name")

  def idxLogin: Index = index("idxLogin", login, unique = true)

  override def * : ProvenShape[(Int, String, String, String)] = (id, login, passHash, name)
}
