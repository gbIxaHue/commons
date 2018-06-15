package ru.dev.gbixahue.library.analysis.handlers

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import ru.dev.gbixahue.library.analysis.event.AnalysisEvent
import ru.dev.gbixahue.library.analysis.event.HightEvent
import java.math.BigDecimal
import java.util.*

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class FacebookAnalysis(application: Application, params: MutableMap<AnalysisKey, Any> = mutableMapOf()): BaseAnalysisSystem(application, params) {

  companion object {
    val ID = FacebookAnalysis::class.java.simpleName
  }

  private var tracker: AppEventsLogger? = null

  override fun initSystem(application: Application) {
    FacebookSdk.sdkInitialize(application)
    tracker = AppEventsLogger.newLogger(application)
    AppEventsLogger.activateApp(application)
  }

  override fun lowPriority(event: AnalysisEvent) {
    handleEvent(getCategoryAction(event).plus(getLogValues(event)),
        { tracker?.logEvent(getCategoryAction(event), toBundle(event.values())) })
  }

  override fun highPriority(event: AnalysisEvent) {
    when (event.action()) {
      HightEvent.ADS_CLICKED.name -> viewedContent(event)
      HightEvent.ORDER_SERVICE.name -> purchaseMethod(event)
      else -> acquiredEvents(event)
    }
  }

  private fun purchaseMethod(event: AnalysisEvent) {
    handleEvent(("Purchase"), { tracker?.logPurchase(BigDecimal(1), Currency.getInstance("USD")) })
  }

  private fun viewedContent(event: AnalysisEvent) {
    handleEvent(("Content viewed"), { tracker?.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, 1.toDouble()) })
  }

  private fun acquiredEvents(event: AnalysisEvent) {
    val catAction = getCategoryAction(event)
    events.filter { it == catAction }
        .map { handleEvent(catAction.plus(getLogValues(event)), { tracker?.logEvent(catAction, toBundle(event.values())) }) }
  }
}
