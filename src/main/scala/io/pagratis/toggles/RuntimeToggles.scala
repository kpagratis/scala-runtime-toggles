package io.pagratis.toggles

object RuntimeToggles {
  def builder(configFilePath: String): RuntimeTogglesBuilder = RuntimeTogglesBuilder(configFilePath)
}


trait RuntimeToggles extends AutoCloseable {
  def isAvailable(toggleName: String): Boolean

  override def close(): Unit = {}
}
