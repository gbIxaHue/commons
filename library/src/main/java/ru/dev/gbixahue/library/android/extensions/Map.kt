package ru.dev.gbixahue.library.android.extensions

import android.util.Log


/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun <K, V> Map<K, V>.print(tag: String = "Map") {
  this.forEach({ Log.d(tag, "${it.key}=${it.value}") })
  Log.d(tag, "")
}