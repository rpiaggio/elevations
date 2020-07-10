import cats.implicits._
import edu.gemini.qpt.core.util.{ImprovedSkyCalc => JavaSkyCalc}
import edu.gemini.spModel.core.Site
import java.{util => ju}
import jsky.coords.WorldCoords
import gem.Target
import gsp.math.ProperMotion
import gsp.math.Epoch
import gsp.math.Coordinates
import gsp.math.skycalc.ImprovedSkyCalc

object Main {
  val M51 =
    Coordinates.fromHmsDms.getOption("13 29 52.698000 +47 11 42.929988").get

  def main(args: Array[String]) {
    println("Hello, World!")
    val calc = new ImprovedSkyCalc(Site.GS)
    val javaCalc = new JavaSkyCalc(Site.GS)
    val t = new ju.Date().getTime()
    val coords: Long => WorldCoords = _ =>
      new WorldCoords(
        M51.ra.toAngle.toDoubleDegrees,
        M51.dec.toAngle.toDoubleDegrees
      )
    calc.calculate(coords.apply(t), new ju.Date(t), false)
    javaCalc.calculate(coords.apply(t), new ju.Date(t), false)
    println(calc.getAltitude)
    println(javaCalc.getAltitude)
  }
}
