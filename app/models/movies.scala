package models

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import scala.collection.mutable.ArrayBuffer

case class movies(
                   title:String,
                   description:String,
                   price:Double,
                   director:String,
                   starring:String,
                   rating:String
                 )

object movies {

  val createMovieForm = Form(
    mapping(
      "title" -> nonEmptyText,
      "description" -> nonEmptyText,
      "price" -> of[Double],
      "director" -> nonEmptyText,
      "starring" -> nonEmptyText,
      "rating" -> nonEmptyText
    )(movies.apply)(movies.unapply)
  )

  val theMovies = ArrayBuffer(
    movies("Movie 1", "This is a movie.", 10.55, "Mr. Director", "John Jackson, Jack Johnson", "PG"),
    movies("Movie 2", "This is also a movie.", 12.34, "Mr. Director", "John Smith, Jane Jones", "12A")
  )



}