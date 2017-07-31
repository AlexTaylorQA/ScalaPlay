package controllers

import javax.inject.Inject

import play.modules.reactivemongo._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api._
import play.api.mvc._
import models.movies
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.api._
import scala.concurrent.Future
import play.api.libs.json._


class Application @Inject()(val reactiveMongoApi: ReactiveMongoApi, val messagesApi: MessagesApi) extends Controller with I18nSupport with MongoController with ReactiveMongoComponents{


  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("movies"))

  def index = Action {
    Ok(views.html.index("Test Output", "Welcome to the home page!"))
  }

  def toView = Action { implicit request: Request[AnyContent] =>
    val formValidationResult = movies.createMovieForm.bindFromRequest
    formValidationResult.fold({
      formWithErrors =>
        BadRequest(views.html.theView(movies.theMovies, formWithErrors))
    }, { widget =>
      widget.mID = movies.theID
      collection.flatMap(_.insert(widget))
      Redirect(routes.Application.listMovies)
    })
  }

  def listMovies = Action.async {

    val cursor: Future[Cursor[movies]] = collection.map {
      _.find(Json.obj()).cursor[movies]
    }

    val futureMoviesList: Future[List[movies]] = cursor.flatMap(_.collect[List]())
    futureMoviesList.map { loadMovies =>
      movies.theMovies.clear()
      for(x <- loadMovies)
        {
          movies.theMovies += x
        }

      movies.theID
      Ok(views.html.theView(movies.theMovies, movies.createMovieForm))
    }

  }


  def toEdit(theID:Int) = Action {
    val oneMovie = movies.theMovies.zipWithIndex.filter(_._1.mID == theID).head._2
    Ok(views.html.theEdit(movies.theMovies, movies.createMovieForm.fill(movies.theMovies(oneMovie)), oneMovie))
  }


  def doEdit = Action { implicit request: Request[AnyContent] =>
    val formValidationResult = movies.createMovieForm.bindFromRequest
    formValidationResult.fold({
      formWithErrors =>
        BadRequest(views.html.theView(movies.theMovies, formWithErrors))
    }, { widget =>

      val modifier = Json.obj("$set" -> Json.obj("title"->widget.title,
        "description"->widget.description,
        "price"->widget.price,
        "director" -> widget.director,
        "starring" -> widget.starring,
        "rating" -> widget.rating
      ))

      collection.map{
        _.findAndUpdate(Json.obj("mID"->widget.mID),modifier)
      }

      Redirect(routes.Application.listMovies)
    })
  }

  def toDel(theID:Int) = Action {
    movies.theMovies.length <= 0 match {
      case true => Ok(views.html.theView(movies.theMovies, movies.createMovieForm))
      case false => collection.map{_.findAndRemove(Json.obj("mID"->theID))
      }
      Redirect(routes.Application.listMovies)
    }
  }

  def triangle = Action {
    var str = ""
    for(x <- 0 to 100)
      {
        str += (" &nbsp;" * (100 - x)) + "/" + (" &nbsp;" * x * 2) + "\\<br>"

      }
    Ok("Test Output\n\n" + str)
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