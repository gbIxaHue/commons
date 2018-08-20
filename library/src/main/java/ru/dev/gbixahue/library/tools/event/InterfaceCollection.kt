package ru.dev.gbixahue.library.tools.event

import android.os.Parcelable

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface InterfaceCollection

interface EventSocket: EventEmitter, EventReceiver

interface EventEmitter {
  val listOfReceivers: MutableList<EventReceiver>

  fun addEventReceiver(receiver: EventReceiver) {
    listOfReceivers.add(receiver)
  }

  fun removeEventReceiver(receiver: EventReceiver) {
    listOfReceivers.remove(receiver)
  }
}

interface EventReceiver {
  fun <D: Parcelable> onEvent(event: Event<D>)
}