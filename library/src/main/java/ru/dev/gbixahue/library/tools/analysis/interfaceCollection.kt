package ru.dev.gbixahue.library.tools.analysis

import ru.dev.gbixahue.library.tools.analysis.event.AnalysisEvent

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface EventEmitter {
  fun send(event: AnalysisEvent)
}

interface AnalysisSystem: EventEmitter {
  fun registerEvent(eventName: String)
}

interface AnalysisHolder: EventEmitter {
  fun addAnalysisSystem(tag: String, trackHandler: AnalysisSystem)
  fun removeAnalysisSystem(tag: String)
}