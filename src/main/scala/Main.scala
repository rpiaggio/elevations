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
import java.time.Instant

object Main {
  val M51 =
    Coordinates.fromHmsDms.getOption("13 29 52.698000 +47 11 42.929988").get

  val Where = Site.GS

  def main(args: Array[String]) = {
    println("Hello, World!")
    val calc = new ImprovedSkyCalc(Where)
    val javaCalc = new JavaSkyCalc(Where)

    val now = Instant.now()
    val judNow = ju.Date.from(now)
    val t = judNow.getTime

    val wcoords =
      new WorldCoords(
        M51.ra.toAngle.toDoubleDegrees,
        M51.dec.toAngle.toDoubleDegrees
      )

    calc.calculate(M51, now, false)
    javaCalc.calculate(wcoords, judNow, false)

    println(calc.getAltitude)
    println(javaCalc.getAltitude)

    println(ju.Date.from(calc.getLst(now).toInstant))
    println(javaCalc.getLst(judNow))
  }
}
