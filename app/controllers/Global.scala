import play.api._
import play.api.mvc._
import play.api.mvc.Results._

object Global extends GlobalSettings {

  /**
   ** 404: When action methods are not found or unable to bind posts
   **/
  override def onHandlerNotFound(request: RequestHeader) = {
    if (request.path != "/favicon.ico" && Logger.isInfoEnabled)
      Logger.info("Not found: " + request.path)
    NotFound(views.html.notFoundPage(request.path))
  }

  /**
   ** When e.g. the parameters mismatch what routes are defined
   **/
  override def onBadRequest(request: RequestHeader, error: String) = {
    BadRequest("Bad Request: " + error)
  }

  /**
   ** 500: When your code is bad...
   **/
  override def onError(request: RequestHeader, ex: Throwable) = {
    Logger.error("Server error: " + request.path,ex)

    InternalServerError(views.html.errorPage(throw ex))
  }
}