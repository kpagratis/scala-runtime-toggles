package io.pagratis.toggles.test

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import io.pagratis.toggles.RuntimeToggles
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.DurationInt

class RuntimeToggleBuilderTest extends AnyFunSuite with Matchers {
  test("builder setters work") {
    val mapper = new YAMLMapper()
    val builder = RuntimeToggles
      .builder("someFilePath")
      .withUpdateDuration(10.minutes)
      .withYAMLMapper(mapper)

    builder.yamlMapper mustBe mapper
    builder.configFilePath mustEqual "someFilePath"
    builder.updateDuration.length mustBe 10
    builder.updateDuration.unit mustBe TimeUnit.MINUTES

    builder.withConfigFilePath("somewhereElse").configFilePath mustEqual "somewhereElse"
  }
}
