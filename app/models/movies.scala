package models

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.libs.json.Json

import scala.collection.mutable.ArrayBuffer

case class movies(
                   var mID:Int,
                   var title:String,
                   var description:String,
                   var price:Double,
                   var director:String,
                   var starring:String,
                   var rating:String
                 )

object movies {

  val createMovieForm = Form(
    mapping(
      "ID" -> default(number, 0),
      "Title" -> nonEmptyText,
      "Description" -> nonEmptyText,
      "Price" -> of[Double],
      "Director" -> nonEmptyText,
      "Starring" -> nonEmptyText,
      "Rating" -> nonEmptyText
    )(movies.apply)(movies.unapply)
  )

  val theMovies:ArrayBuffer[movies] = ArrayBuffer()
/*
    movies(
      0, "Iron Man",
      "A billionaire industrialist and genius inventor, Tony Stark, is conducting weapons tests overseas, but terrorists kidnap him to force him to build a devastating weapon. Instead, he builds an armored suit and upends his captors. Returning to America, Stark refines the suit and uses it to combat crime and terrorism.",
      10.55,
      "Jon Favreau",
      "Robert Downey Jr, Gwyneth Paltrow, Terrence Howard, Jeff Bridges, Jon Favreau, Paul Bettany",
      "12A"),

    movies(
      1, "The Incredible Hulk",
      "Scientist Bruce Banner desperately seeks a cure for the gamma radiation that contaminated his cells and turned him into The Hulk. Cut off from his true love Betty Ross and forced to hide from his nemesis, Gen. Thunderbolt Ross, Banner soon comes face-to-face with a new threat: a supremely powerful enemy known as The Abomination.",
      12.34,
      "Louis Leterrier",
      "Edward Norton, Liv Tyler, William Hurt, Tim Roth, Tim Blake Nelson, Lou Ferrigno",
      "12A"),

    movies(
      2, "Iron Man 2",
      "With the world now aware that he is Iron Man, billionaire inventor Tony Stark faces pressure from all sides to share his technology with the military. He is reluctant to divulge the secrets of his armored suit, fearing the information will fall into the wrong hands. With Pepper Potts and James \"Rhodey\" Rhodes by his side, Tony must forge new alliances and confront a powerful new enemy.",
      8.59,
      "Jon Favreau",
      "Robert Downey Jr, Gwyneth Paltrow, Don Cheadle, Mickey Rourke, Sam Rockwell, Scarlett Johansson, Jon Favreau, Samuel L Jackson, Paul Bettany",
      "12A"),

    movies(
      3, "Thor",
      "As the son of Odin, king of the Norse gods, Thor will soon inherit the throne of Asgard from his aging father. However, on the day that he is to be crowned, Thor reacts with brutality when the gods' enemies, the Frost Giants, enter the palace in violation of their treaty. As punishment, Odin banishes Thor to Earth. While Loki, Thor's brother, plots mischief in Asgard, Thor, now stripped of his powers, faces his greatest threat.",
      10.24,
      "Kenneth Branagh",
      "Chris Hemsworth, Natalie Portman, Tom Hiddleston, Anthony Hopkins, Idris Elba, Stellan Skarsgård, Clark Gregg",
      "12A"),

    movies(
      4, "Captain America - The First Avenger",
      "It is 1941 and the world is in the throes of war. Steve Rogers wants to do his part and join America's armed forces, but the military rejects him because of his small stature. Finally, Steve gets his chance when he is accepted into an experimental program that turns him into a supersoldier called Captain America. Joining forces with Bucky Barnes and Peggy Carter, Captain America leads the fight against the Nazi-backed HYDRA organization.",
      9.99,
      "Joe Johnston",
      "Chris Evans, Sebastian Stan, Hayley Atwell, Hugo Weaving, Toby Jones, Dominic Cooper, Tommy Lee Jones, Stanley Tucci",
      "12A"),

    movies(
      5, "The Avengers",
      "When Thor's evil brother, Loki, gains access to the unlimited power of the energy cube called the Tesseract, Nick Fury, director of S.H.I.E.L.D., initiates a superhero recruitment effort to defeat the unprecedented threat to Earth. Joining Fury's \"dream team\" are Iron Man, Captain America, the Hulk, Thor, the Black Widow and Hawkeye.",
      15.63,
      "Joss Whedon",
      "Robert Downey Jr, Chris Evans, Chris Hemsworth, Mark Ruffalo, Scarlett Johansson, Jeremy Renner, Samuel L Jackson, Tom Hiddleston, Stellan Skarsgård, Clark Gregg, Cobie Smulders",
      "12A"),

    movies(
      6, "Iron Man 3",
      "Plagued with worry and insomnia since saving New York from destruction, Tony Stark, now, is more dependent on the suits that give him his Iron Man persona -- so much so that every aspect of his life is affected, including his relationship with Pepper. After a malevolent enemy known as the Mandarin reduces his personal world to rubble, Tony must rely solely on instinct and ingenuity to avenge his losses and protect the people he loves.",
      10.44,
      "Shane Black",
      "Robert Downey Jr, Gwyneth Paltrow, Don Cheadle, Guy Pearce, Ben Kingsley, Rebecca Hall, Jon Favreau, Paul Bettany",
      "12A"),

    movies(
      7, "Thor - The Dark World",
      "In ancient times, the gods of Asgard fought and won a war against an evil race known as the Dark Elves. The survivors were neutralized, and their ultimate weapon -- the Aether -- was buried in a secret location. Hundreds of years later, Jane Foster finds the Aether and becomes its host, forcing Thor to bring her to Asgard before Dark Elf Malekith captures her and uses the weapon to destroy the Nine Realms -- including Earth.",
      8.92,
      "Alan Taylor",
      "Chris Hemsworth, Natalie Portman, Christopher Eccleston, Tom Hiddleston, Anthony Hopkins, Idris Elba, Stellan Skarsgård",
      "12A")
*/


  def theID =
    {
      theMovies.length match {
        case x if x > 0 => theMovies.sortWith((x, y) => x.mID > y.mID).head.mID + 1
        case _ => 0
      }
    }

  implicit val feedFormat = Json.format[movies]

}
