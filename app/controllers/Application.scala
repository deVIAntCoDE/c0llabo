package controllers

import play.api._
import play.api.mvc._
import com.mpowerpayments.mpower.MPowerSetup

import play.api.data._
import play.api.data.Forms._
import models.User


object Application extends Controller {

  val userForm = Form(
    tuple(
    "email" -> nonEmptyText,
    "name" -> nonEmptyText,
    "password" -> nonEmptyText
    )
  )
  
  def index = Action {
    Ok(views.html.index())
//    Redirect(routes.Application.users)
  }

  def about = Action {
    Ok (views.html.about())
  }

  def users = Action{
   Ok(views.html.test(User.findAll,userForm))
  }

  def newUser = Action{ implicit  request =>
    userForm.bindFromRequest.fold(
      errors => BadRequest(views.html.test(User.findAll,errors)),
      user =>{
        User.create(User(user._1,user._2,user._3))
        Redirect(routes.Application.users)
      }

    )
  }

  def deleteUser(email:String) = Action{
    User.delete(email)
    Redirect(routes.Application.users)
  }






  
}