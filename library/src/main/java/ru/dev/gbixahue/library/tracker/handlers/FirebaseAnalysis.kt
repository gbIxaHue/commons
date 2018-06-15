package ru.dev.gbixahue.library.tracker.handlers

import android.app.Application
import ru.dev.gbixahue.library.tracker.event.AnalysisEvent
import kotlin.jvm.java

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class FirebaseAnalysis(application: Application, params: MutableMap<AnalysisKey, Any> = mutableMapOf()): BaseAnalysisSystem(application, params) {

  companion object {
    val ID = FirebaseAnalysis::class.java.simpleName
  }

  private var tracker: FirebaseAnalytics? = null

  override fun initSystem(application: Application) {
    tracker = FirebaseAnalytics.getInstance(application)
  }

  override fun lowPriority(event: AnalysisEvent) {
    sendEvent(event)
  }

  override fun highPriority(event: AnalysisEvent) {}

  private fun sendEvent(event: AnalysisEvent) {
    val catAction = getCategoryAction(event)
    handleEvent(catAction.plus(getLogValues(event)), { tracker?.logEvent(catAction, toBundle(event.values())) })
  }

  override fun getCategoryAction(event: AnalysisEvent): String {
    return event.getEventName { category, action ->
      if (category.isEmpty()) action
      else if (action.isEmpty()) category
      else category + " " + action
    }
  }
}