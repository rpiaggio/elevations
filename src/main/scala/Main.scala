import cats.implicits._
import edu.gemini.qpt.core.util.{ImprovedSkyCalc => JavaSkyCalc}
import edu.gemini.spModel.core.{Site => SpSite}
import java.{util => ju}
import jsky.coords.WorldCoords
import gem.Target
import gsp.math.ProperMotion
import gsp.math.Epoch
import gsp.math.Coordinates
import gsp.math.skycalc.ImprovedSkyCalc
import java.time.Instant
import gem.enum.Site

object Main {
  val M51 =
    Coordinates.fromHmsDms.getOption("13 29 52.698000 +47 11 42.929988").get

  private def wCoords(coords: Coordinates): WorldCoords =
    new WorldCoords(
      coords.ra.toAngle.toDoubleDegrees,
      coords.dec.toAngle.toDoubleDegrees
    )

  def main(args: Array[String]) = {
    println("Hello, World!")

    val site = Site.GN
    val spSite = SpSite.GN

    val coords = M51

    val calc = new ImprovedSkyCalc(site)
    val javaCalc = new JavaSkyCalc(spSite)

    val now = Instant.now()
    val judNow = ju.Date.from(now)
    val t = judNow.getTime

    calc.calculate(coords, now, false)
    javaCalc.calculate(wCoords(coords), judNow, false)

    println(calc.getAltitude)
    println(javaCalc.getAltitude)

    println(ju.Date.from(calc.getLst(now).toInstant))
    println(javaCalc.getLst(judNow))
  }
}
