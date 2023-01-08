package io.pagratis.toggles.test

import io.pagratis.toggles.{RuntimeTestToggles, RuntimeToggles}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

import java.io.{File, FileNotFoundException}

class RuntimeTogglesTest extends AnyFunSuite with Matchers {
  test("honor percentages") {
    val toggles = RuntimeToggles(new File("testData/toggles.yaml"))
    toggles.load()
    run1000Times(toggles, "alwaysOn") mustBe 1000
    run1000Times(toggles, "alwaysOff") mustBe 0
    val half = run1000Times(toggles, "halfOn")
    val beWithinTolerance = be >= 450 and be <= 550
    half must beWithinTolerance
  }

  test("handle a missing file") {
    intercept[FileNotFoundException] {
      RuntimeToggles(new File("fileDoesNotExistLetsFail")).load()
    }
  }

  test("RuntimeTestToggles can be used in place of RuntimeToggles") {
    val testToggles = new RuntimeTestToggles()
    run1000Times(testToggles, "anything") mustBe 0
    testToggles.makeAvailable("something")
    run1000Times(testToggles, "something") mustBe 1000
    testToggles.makeUnAvailable("something")
    run1000Times(testToggles, "something") mustBe 0
  }

  private def run1000Times(runtimeToggles: RuntimeToggles, name: String): Int = {
    (1 to 1000)
      .map(_ => runtimeToggles.isAvailable(name))
      .count(v => v)
  }
}
