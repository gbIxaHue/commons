package ru.dev.gbixahue.library.analysis.handlers

import android.app.Application
import android.os.Bundle
import ru.dev.gbixahue.library.analysis.AnalysisSystem
import ru.dev.gbixahue.library.analysis.event.AnalysisEvent
import ru.dev.gbixahue.library.analysis.event.EPriority
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.utils.stringOf
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
abstract class BaseAnalysisSystem(private val application: Application, private val params: MutableMap<AnalysisKey, Any>): AnalysisSystem {

  var enableLog: Boolean = false

  protected open val events = mutableListOf<String>()
  protected var isInit = AtomicBoolean(false)

  private val stackOfEvents: Queue<AnalysisEvent> = LinkedList<AnalysisEvent>()
  private val lock = ReentrantLock()

  override fun send(event: AnalysisEvent) {
    if (! isInit.get()) {
      initAndSend(event)
      return
    }
    innerSend(event)
  }

  private fun initAndSend(event: AnalysisEvent) {
    stackOfEvents.offer(event)
    lock.lock()
    initSystem(application)
    lock.unlock()
    isInit.set(true)
    stackOfEvents.forEach { innerSend(it) }
  }

  private fun innerSend(event: AnalysisEvent) {
    when (event.priority()) {
      EPriority.LOW -> lowPriority(event)
      EPriority.HIGH -> highPriority(event)
    }
  }

  protected abstract fun initSystem(application: Application)

  override fun registerEvent(eventName: String) {
    events.add(eventName)
  }

  protected open fun lowPriority(event: AnalysisEvent) {}
  protected open fun highPriority(event: AnalysisEvent) {}

  protected open fun getCategoryAction(event: AnalysisEvent): String {
    return event.getEventName { category, action ->
      if (category.isEmpty()) action
      else if (action.isEmpty()) category
      else category + " / " + action
    }
  }

  open fun getLogValues(event: AnalysisEvent): String {
    if (event.values() == null) return ""
    return ", with values = ".plus(stringOf(event.values()))
  }

  open fun handleEvent(event: String, send: () -> Unit) {
    if (enableLog) Log.d(this, event)
    else send()
  }

  protected fun toBundle(map: MutableMap<String, Any>?): Bundle? {
    if (map == null) return null
    val bundle = Bundle()
    for ((key, value) in map) {
      bundle.putString(key, stringOf(value))
    }
    return bundle
  }
}