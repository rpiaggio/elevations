import cats.implicits._
import edu.gemini.skycalc.{ImprovedSkyCalcTest => JavaSkyCalcTest}
import gsp.math.Coordinates
import gsp.math.skycalc.ImprovedSkyCalc
import java.time.Instant
import gem.enum.Site

object Main {
  val M51 =
    Coordinates.fromHmsDms.getOption("13 29 52.698000 +47 11 42.929988").get

  private val NanosPerMillis: Int = 1_000_000

  private def truncateInstantToMillis(i: Instant): Instant = {
    Instant.ofEpochSecond(
      i.getEpochSecond,
      i.getNano / NanosPerMillis * NanosPerMillis
    )
  }

  def main(args: Array[String]) = {
    println("Hello, World!")

    val site = Site.GN

    // val coords = M51
    val coords = Coordinates.Zero

    val calc = new ImprovedSkyCalc(site)
    val javaCalc = new JavaSkyCalcTest(
      site.longitude.toDoubleDegrees,
      site.latitude.toDoubleDegrees,
      site.altitude
    )

    val now = truncateInstantToMillis(Instant.now())

    calc.calculate(coords, now, false)
    javaCalc.calculate(
      coords.ra.toAngle.toDoubleDegrees,
      coords.dec.toAngle.toDoubleDegrees,
      now,
      false
    )

    println(calc.getAltitude)
    println(javaCalc.getAltitude)
    println(calc.getAltitude == javaCalc.getAltitude)

    println(truncateInstantToMillis(calc.getLst(now).toInstant).toEpochMilli)
    println(javaCalc.getLst(now).toInstant.toEpochMilli)
  }
}
