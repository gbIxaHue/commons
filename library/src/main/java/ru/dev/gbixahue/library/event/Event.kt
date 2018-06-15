package ru.dev.gbixahue.library.event

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class Event<D>(val type: EventType, val data: D?) {

  override fun toString(): String {
    return "type: " + type.name + "response: " + (data?.toString() ?: "")
  }
}
