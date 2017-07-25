package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    var str = ""
    for(x <- 0 to 100)
      {
        str += (" &nbsp;" * (100 - x)) + "/" + (" &nbsp;" * x * 2) + "\\<br>"

      }
    Ok(views.html.index("Test Output", str))
  }

  def doRedirect = Action {
     Redirect("http://www.google.com")
  }

  def theDynamic(id:Int) = Action{
    Ok("The dynamic ID is: " + id)
  }

  def theStatic(id:Int) = Action{
    Ok("The static ID is always: " + id)
  }

  def theDefault(id:Int) = Action{
    Ok("The ID given is: " + id + ", and the default ID is always: " + 1 )
  }

  def theOptional(id:Option[String]) = Action{
    Ok("The optional ID is: " + id.getOrElse("No ID inputted."))
  }

  def hello(name: String) = Action{
    Ok("Hello " + name + "!").withCookies(Cookie("theme", "HelloCookie"))
  }

  def helloPrintCookie = Action {request =>
     request.cookies.get("theme").map{
      theCookie => Ok("Description: " + theCookie.value)
    }.getOrElse{
      Unauthorized("Error: There is no cookie available.")
    }
  }

  /*
  def helloChangeCookie(newName:String) = Action{ implicit request =>
    request.cookies.get("theme").get.name = "newName"
    Ok()
  }
  */   // Attempted to rename cookie. Cookie name is a val so this failed.

  def helloDiscardCookie = Action {
    Ok("The cookie was deleted.").discardingCookies(DiscardingCookie("theme"))
  }

  def dummy(name:String) = TODO


  // Only one of the DoubleTry functions will be called: doubleTryOne
  def doubleTryOne = Action{
    Ok("The first doubleTry function was called.")
  }

  def doubleTryTwo = Action{
    Ok("The second doubleTry function was called.")
  }


}