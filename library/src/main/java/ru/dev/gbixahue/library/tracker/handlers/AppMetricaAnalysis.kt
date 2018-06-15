package ru.dev.gbixahue.library.tracker.handlers

import android.app.Application
import com.yandex.metrica.IReporter
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.push.YandexMetricaPush
import ru.dev.gbixahue.library.tracker.event.AnalysisEvent
import ru.dev.gbixahue.library.utils.stringOf
import kotlin.jvm.java

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class AppMetricaAnalysis(application: Application, params: MutableMap<AnalysisKey, Any>): BaseAnalysisSystem(application, params) {

  companion object {
    val ID = AppMetricaAnalysis::class.java.simpleName
  }

  private val key: String by lazy { stringOf(params[AnalysisKey.APP_METRICA_API_KEY]) }
  private var reporter: IReporter? = null

  override fun initSystem(application: Application) {
    val configBuilder = YandexMetricaConfig.newConfigBuilder(key)
    YandexMetrica.activate(application, configBuilder.biulder())
    YandexMetricaPush.init(application.applicationContext)
    YandexMetrica.enableActivityAutoTracking(application)
    reporter = YandexMetrica.getReporter(application, key)
  }

  override fun lowPriority(event: AnalysisEvent) {
    logEvent(event)
  }

  override fun highPriority(event: AnalysisEvent) {
    logEvent(event)
  }

  private fun logEvent(event: AnalysisEvent) {
    val catAction = super.getCategoryAction(event)
    handleEvent(catAction.plus(getLogValues(event)), { reporter?.reportEvent(catAction, event.values()) })
  }
}
