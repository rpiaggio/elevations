package gsp.math.skycalc

import cats.implicits._
import weaver._
import weaver.scalacheck._

import gem.enum.Site
import gem.arb.ArbEnumerated._
import edu.gemini.skycalc.{ ImprovedSkyCalcTest => JavaSkyCalcTest }
import cats.Show
import java.time.Instant
import gsp.math.Coordinates
import gsp.math.arb.ArbCoordinates._
import com.fortysevendeg.scalacheck.datetime.instances.jdk8._
import com.fortysevendeg.scalacheck.datetime.jdk8.ArbitraryJdk8._
import com.fortysevendeg.scalacheck.datetime.GenDateTime.genDateTimeWithinRange
import java.time.ZonedDateTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.Period
import java.time.Duration

object ImprovedSkyCalcSpec extends SimpleIOSuite with IOCheckers {

  implicit val showSite: Show[Site]         = Show.fromToString
  implicit val showInstant: Show[Instant]   = Show.fromToString
  implicit val showZDT: Show[ZonedDateTime] = Show.fromToString

  private val NanosPerMillis: Int = 1_000_000

  private val zdtFrom  = ZonedDateTime.of(
    LocalDate.of(1901, 1, 1),
    LocalTime.MIDNIGHT,
    ZoneOffset.UTC
  )
  private val zdtRange = Duration.ofDays(Period.ofYears(1000).getDays)

  private def truncateInstantToMillis(i: Instant): Instant =
    Instant.ofEpochSecond(
      i.getEpochSecond,
      i.getNano / NanosPerMillis * NanosPerMillis
    )

  simpleTest("Arbitrary sky calculations") {
    forall { (site: Site) =>
      // This generator already provides ZDTs with millisecond precision, not nano.
      forall(genDateTimeWithinRange(zdtFrom, zdtRange)) { zdt =>
        forall { coords: Coordinates =>
          val calc     = new ImprovedSkyCalc(site)
          val javaCalc = new JavaSkyCalcTest(
            site.longitude.toDoubleDegrees,
            site.latitude.toDoubleDegrees,
            site.altitude
          )

          val instant = zdt.toInstant

          val sdt  = calc.calculate(coords, instant, false)
          val jdts = javaCalc.calculate(
            coords.ra.toAngle.toDoubleDegrees,
            coords.dec.toAngle.toDoubleDegrees,
            instant,
            false
          )

          expect(calc.getAltitude == javaCalc.getAltitude)
          expect(calc.getAzimuth == javaCalc.getAzimuth)
          expect(calc.getParallacticAngle == javaCalc.getParallacticAngle)
          expect(calc.getHourAngle == javaCalc.getHourAngle)
          expect(calc.getLunarIlluminatedFraction == javaCalc.getLunarIlluminatedFraction)
          expect(calc.getLunarSkyBrightness == javaCalc.getLunarSkyBrightness)
          expect(calc.getTotalSkyBrightness == javaCalc.getTotalSkyBrightness)
          expect(calc.getLunarPhaseAngle == javaCalc.getLunarPhaseAngle)
          expect(calc.getSunAltitude == javaCalc.getSunAltitude)
          expect(calc.getLunarDistance == javaCalc.getLunarDistance)
          expect(calc.getLunarElevation == javaCalc.getLunarElevation)
        }
      }
    }
  }
}
