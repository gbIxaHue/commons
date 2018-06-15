package ru.dev.gbixahue.library.extensions

import ru.dev.gbixahue.library.android.log.Log

/**
 * Created by Anton Zhilenkov on 26.10.17.
 */
fun <K, V> Map<K, V>.print(tag: String = "Map") {
  this.forEach({ Log.d(tag, "${it.key}=${it.value}") })
  Log.d(tag, "")
}

fun <K, V> Map<K, V>.stringOf(nameOf: String = ""): String{
  if (this.isEmpty()) return ""
  val sBuilder = StringBuilder(if (nameOf.isEmpty()) "[ " else "$nameOf [ ")
  this.forEach({ sBuilder.append("${it.key}=${it.value}").append(", ") })
  sBuilder.delete(sBuilder.length-2, sBuilder.length).append(" ]")
  return sBuilder.toString()
}