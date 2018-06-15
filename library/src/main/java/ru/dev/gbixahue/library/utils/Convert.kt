package ru.dev.gbixahue.library.utils

import android.content.Context

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun convertDpToPx(context: Context, dp: Float): Float {
  return dp * (context.resources.displayMetrics.densityDpi / 160f)
}

fun stringOf(value: Any?): String {
  if (value == null) return ""
  if (value is String) return value
  return value.toString()
}

fun booleanOf(value: Any?): Boolean {
  if (value == null) return false
  if (value is Boolean) return value
  return true
}