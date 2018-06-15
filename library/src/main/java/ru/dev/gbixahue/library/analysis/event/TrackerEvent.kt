package ru.dev.gbixahue.library.analysis.event

import ru.dev.gbixahue.library.utils.stringOf

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class TrackerEvent(
    private val category: String,
    private val action: String,
    private val priority: EPriority)
  : AnalysisEvent {

  constructor(category: String, action: String): this(category, action, EPriority.LOW)

  private var params: MutableMap<String, Any>? = null
    private set

  override fun category(): String = category
  override fun action(): String = action
  override fun priority(): EPriority = priority
  override fun values(): MutableMap<String, Any>? = params
  override fun getEventName(convert: (String, String) -> String): String {
    return convert(stringOf(category), stringOf(action))
  }

  fun withValues(newValues: Map<String, Any>): TrackerEvent {
    if (params == null) params = mutableMapOf()
    params !!.putAll(newValues)
    return this
  }

  fun withValues(key: String, value: Any): TrackerEvent {
    if (params == null) params = mutableMapOf()
    params !!.put(key, value)
    return this
  }
}
