package controllers

import play.Logger
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._



/**
 * Created with IntelliJ IDEA.
 * User: henry
 * Date: 21/02/2013
 * Time: 10:44
 * To change this template use File | Settings | File Templates.
 */
object PizzaOrder extends Controller{

  val pizzaForm = Form(
    tuple(
      "pizzaType" -> nonEmptyText(maxLength = 128),
      "size" -> nonEmptyText(maxLength = 128),
      "address" -> nonEmptyText(maxLength = 128)
    )
  )

  def showOrderForm = Action {
    Ok(views.html.pizza.orderform(pizzaForm))
  }


  def orderPizza = Action { implicit request =>
    pizzaForm.bindFromRequest.fold(
      errors => BadRequest(views.html.pizza.orderform(errors)),
      pizzaOrder => {
     //   Logger.info("Ordering pizza: "pizzaForm.pizzaType)
        // business order logic
        Redirect(routes.PizzaOrder.pizzaOrdered("magaritha"))
      }
    )
  }

  def pizzaOrdered(pizzaType: String) = Action {
    Ok(views.html.pizza.ordered(pizzaType))
  }


}
