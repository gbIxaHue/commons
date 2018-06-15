package ru.dev.gbixahue.library.analysis.event

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface AnalysisEvent {
  fun category(): String
  fun action(): String
  fun getEventName(convert: (String, String) -> String): String
  fun values(): MutableMap<String, Any>?
  fun priority(): EPriority
}
