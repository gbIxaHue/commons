package ru.dev.gbixahue.library.tools

import android.app.Application
import com.crashlytics.android.Crashlytics
import ru.dev.gbixahue.library.android.log.LogHandler
import ru.dev.gbixahue.library.tools.analysis.AnalysisHolder
import ru.dev.gbixahue.library.tools.analysis.event.AnalysisEvent
import ru.dev.gbixahue.library.tools.analysis.handlers.BaseAnalysisSystem

/**
 * Created by Anton Zhilenkov on 14.11.18.
 */
class FabricLogger(application: Application): BaseAnalysisSystem(application, mutableMapOf()), LogHandler {


	override fun initSystem(application: Application) {}

	fun withTrackManager(tracker: AnalysisHolder) {
		tracker.addAnalysisSystem(javaClass.simpleName, this)
	}

	override fun handleLog(logMessage: String) {
		Crashlytics.log(logMessage)
	}

	override fun send(event: AnalysisEvent) {
		Crashlytics.log(assemblyEvent(event))
	}

	private fun assemblyEvent(event: AnalysisEvent): String {
		val catAction = getCategoryAction(event)
		val values = getLogValues(event)
		return catAction + values
	}
}