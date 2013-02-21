package controllers

import play.api._
import play.api.mvc._
import com.mpowerpayments.mpower.MPowerSetup


object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index())
  }

  def about = Action {
    Ok (views.html.about())
  }




  
}