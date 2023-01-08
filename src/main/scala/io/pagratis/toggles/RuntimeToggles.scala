package io.pagratis.toggles

object RuntimeToggles {
  def builder() = RuntimeTogglesBuilder()
}



trait RuntimeToggles extends AutoCloseable {
  def isAvailable(toggleName: String): Boolean

  override def close(): Unit = {}
}
