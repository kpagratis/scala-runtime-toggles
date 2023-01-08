package io.pagratis.toggles
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper

import java.io.File

object RuntimeToggles {
  def apply(configFile: File): RuntimeTogglesImpl =
    new RuntimeTogglesImpl(configFile, new YAMLMapper())

  def apply(configFile: File, yamlMapper: YAMLMapper): RuntimeTogglesImpl =
    new RuntimeTogglesImpl(configFile, yamlMapper)

  def main(args: Array[String]): Unit = {
    val toggles = RuntimeToggles(new File("testData/toggles.yaml"))
    toggles.isAvailable("adf")
    toggles.close()
  }
}


trait RuntimeToggles extends AutoCloseable {
  def isAvailable(toggleName: String): Boolean

  override def close(): Unit = {}
}
