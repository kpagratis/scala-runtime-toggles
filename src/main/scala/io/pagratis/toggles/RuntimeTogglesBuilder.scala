package io.pagratis.toggles

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper

import java.io.File
import scala.concurrent.duration.{Duration, DurationInt}

case class RuntimeTogglesBuilder(
                             configFilePath: String = "",
                             yamlMapper: YAMLMapper = new YAMLMapper(),
                             updateDuration: Duration = 10.seconds
                           ) {
  def withUpdateDuration(duration: Duration): RuntimeTogglesBuilder = copy(updateDuration = duration)
  def withYAMLMapper(mapper: YAMLMapper): RuntimeTogglesBuilder = copy(yamlMapper = mapper)
  def withConfigFilePath(path: String): RuntimeTogglesBuilder = copy(configFilePath = path)

  def build(): RuntimeTogglesImpl = {
    new RuntimeTogglesImpl(new File(configFilePath), yamlMapper, updateDuration)
  }
}
