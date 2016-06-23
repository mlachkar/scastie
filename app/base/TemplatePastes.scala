package base

import com.olegych.scastie.PastesActor.Paste
import java.util.concurrent.atomic.AtomicLong
import com.olegych.scastie.PastesActor

/**
  */
object TemplatePastes {
  private val pasteIds = new AtomicLong(1L) {
    def next = incrementAndGet()
  }

  val default = nextPaste( """
/***
scalaVersion := "2.11.8"

*/
object Main extends App {
}
                           """)

  val templates = {
    List(
      "typelevel" -> nextPaste( """
/***
scalaVersion := "2.11.8"
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.7.1")
libraryDependencies ++= {
  val scalazVersion = "7.2.2"
  val scalazStreamVersion = "0.8.1a"
  val shapelessVersion = "2.2.5"
  val monocleVersion = "1.2.1"
  val spireVersion = "0.11.0"
  Seq(
    "org.scalaz" %% "scalaz-core" % scalazVersion,
    "org.scalaz" %% "scalaz-concurrent" % scalazVersion,
    "com.chuusai" %% "shapeless" % shapelessVersion,
    "com.github.julien-truffaut" %% "monocle-generic" % monocleVersion,
    "com.github.julien-truffaut" %% "monocle-law" % monocleVersion,
    "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
    "org.spire-math" %% "spire" % spireVersion,
    "org.scalaz.stream" %% "scalaz-stream" % scalazStreamVersion
  )
}
*/
object Main extends App {
  import scalaz._, Scalaz._
  println(List(some(1), none).suml)

  import shapeless._
  object combine extends Poly2 {
    implicit def caseCharString = at[Char, String]((c, s) => s.indexOf(c))
    implicit def caseIntBoolean = at[Int, Boolean]((i, b) => if ((i >= 0) == b) "pass" else "fail")
  }
  val l1 = "foo" :: true :: HNil
  val f1 = l1.foldLeft('o')(combine)
  println(f1)

  import spire.math._ // provides functions, types, and type classes

  import spire.implicits._ // provides infix operators, instances and conversions

  import spire.random._

  import scalaz.stream._
  import scalaz.concurrent.Task

  import monocle._
  import monocle.syntax._

  import monocle.std.string._ // to get String instance for HeadOption


}

        """)
      ,
      "typesafe" -> nextPaste( """
/***
scalaVersion := "2.11.8"

libraryDependencies ++= Seq("com.typesafe.play" %% "play" % "2.5.3")
*/
import play.api._
import play.api.mvc._
object Main extends App {
  import play.api.data.Form
  import play.api.data.Forms._
  case class A(i: Int)
  val f = Form(mapping("i" -> number)(A.apply)(A.unapply))
  println(f.fill(A(1)).get)
}
                                """)
      ,
      "sbt 0.13" -> nextPaste( """
/***
sbtPlugin := true
*/
import sbt._
import Keys._
object Build extends Build {
  val p = project.settings(scalaVersion := name.value)
}
                          """)
      ,
      "scala 2.12" -> nextPaste( """
/***
scalaVersion := "2.12.0-M4"

*/
object Main extends App {
}
                          """)
      ,
      "dotty" -> nextPaste( """
/***
enablePlugins(DottyPlugin)
*/
object Main extends App {
}
                          """)
    )
  }

  def nextPaste(x: String): PastesActor.Paste = Paste(pasteIds.next, Some(x), None, None, None)
}