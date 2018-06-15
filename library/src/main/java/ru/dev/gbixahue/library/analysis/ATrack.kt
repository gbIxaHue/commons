package ru.dev.gbixahue.library.analysis

import ru.dev.gbixahue.library.analysis.event.AnalysisEvent
import java.util.*
import java.util.concurrent.Executors

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
object ATrack: AnalysisHolder {

  private val analysisMap = TreeMap<String, AnalysisSystem>()
  private val forAdd = TreeMap<String, AnalysisSystem>()
  private val forRemove: MutableList<String> = mutableListOf()
  private var wasChanged = false

  private val poolExecutor = Executors.newFixedThreadPool(2)

  override fun addAnalysisSystem(tag: String, trackHandler: AnalysisSystem) {
    wasChanged = true
    forAdd.put(tag, trackHandler)
  }

  override fun removeAnalysisSystem(tag: String) {
    wasChanged = true
    forRemove.remove(tag)
  }

  override fun send(event: AnalysisEvent) {
    copy()
    poolExecutor.execute { for (trackerHandler in analysisMap.values) trackerHandler.send(event) }
  }

  private fun copy() {
    if (! wasChanged) return
    if (forAdd.isNotEmpty()) {
      forAdd.forEach { analysisMap.put(it.key, it.value) }
      forAdd.clear()
    }
    if (forRemove.isNotEmpty()) {
      forRemove.forEach { analysisMap.remove(it) }
      forRemove.clear()
    }
    wasChanged = false
  }
}