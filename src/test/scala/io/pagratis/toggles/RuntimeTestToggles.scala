package io.pagratis.toggles

import scala.collection.mutable

class RuntimeTestToggles extends RuntimeToggles {
  private val set = new mutable.HashSet[String]()

  override def isAvailable(toggleName: String): Boolean = set.contains(toggleName)

  def makeAvailable(toggleName: String): Unit = set.add(toggleName)

  def makeUnAvailable(toggleName: String): Unit = set.remove(toggleName)
}
