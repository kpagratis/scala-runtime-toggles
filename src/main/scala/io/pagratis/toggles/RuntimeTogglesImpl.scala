package io.pagratis.toggles

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper

import java.io.{File, FileNotFoundException}
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.{ConcurrentHashMap, Executors, TimeUnit}
import scala.collection.concurrent
import scala.concurrent.duration.Duration
import scala.jdk.CollectionConverters.{ConcurrentMapHasAsScala, IteratorHasAsScala}

class RuntimeTogglesImpl private[toggles](configFile: File, yamlMapper: YAMLMapper, updateDuration: Duration)
  extends RuntimeToggles with AutoCloseable {

  private val loaded = new AtomicBoolean(false)
  private val loading = new AtomicBoolean(false)
  private val map: concurrent.Map[String, Double] = new ConcurrentHashMap[String, Double]().asScala
  private val scheduler = Executors.newSingleThreadScheduledExecutor()

  def load(): Unit = {
    loaded synchronized {
      if (loading.compareAndSet(false, true)) {
        updateValues()
        scheduler.scheduleAtFixedRate(
          () => updateValues(),
          updateDuration.length,
          updateDuration.length,
          updateDuration.unit)
        loaded.set(true)
      }
    }
  }

  override def isAvailable(toggleName: String): Boolean = {
    if (!loaded.get()) load()

    map.getOrElse(toggleName, 0.0) match {
      case 1.0 => true
      case 0.0 => false
      case percentage =>
        Math.random() <= percentage
    }
  }

  private def updateValues(): Unit = {
    if(!configFile.exists()) throw new FileNotFoundException(s"${configFile.getAbsolutePath} was not found")

    val yamlObject = yamlMapper.readTree(configFile)
    yamlObject.fields().asScala.foreach { entry =>
      val name = entry.getKey
      val value = entry.getValue.get("value").asDouble()
      map
        .putIfAbsent(name, value)
        .foreach(_ => map.replace(name, value))
    }
  }

  override def close(): Unit = scheduler.shutdownNow()
}