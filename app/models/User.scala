package models

/**
 * Created with IntelliJ IDEA.
 * User: henry
 * Date: 19/02/13
 * Time: 23:10
 * To change this template use File | Settings | File Templates.
 */
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(email: String, name: String, password: String, admin:Boolean = false)

object User {

  // -- Parsers

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[String]("user.email") ~
    get[String]("user.name") ~
    get[String]("user.password")~
    get[Boolean]("user.admin") map {
      case email~name~password~admin => User(email, name, password,admin)
    }
  }

  // -- Queries

  /**
   * Retrieve a User from email.
   */
  def findByEmail(email: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user where email = {email}").on(
        'email -> email
      ).as(User.simple.singleOpt)
    }
  }

  /**
   * Retrieve all users.
   */
  def findAll: Seq[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user").as(User.simple *)
    }
  }

  /**
   * Authenticate a User.
   */
  def authenticate(email: String, password: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
         select * from user where
         email = {email} and password = {password}
        """
      ).on(
        'email -> email,
        'password -> password
      ).as(User.simple.singleOpt)
    }
  }

  /**
   * Create a User.
   */
  def create(user: User): User = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into user values (
            {email}, {name}, {password},{admin}
          )
        """
      ).on(
        'email -> user.email,
        'name -> user.name,
        'password -> user.password,
        'admin -> user.admin
      ).executeUpdate()

      user

    }
  }

  def delete (email:String) {
    DB.withConnection{ implicit  connection =>
      SQL(
          "delete from user where email = {email}"
      ).on(
      'email ->email
      ).executeUpdate()
    }

  }

}
