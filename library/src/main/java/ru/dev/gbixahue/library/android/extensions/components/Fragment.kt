package ru.dev.gbixahue.library.android.extensions.components

import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun Fragment.showToast(messageResId: Int) {
  showToast(resources.getString(messageResId))
}

fun Fragment.showToast(message: String) {
  Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.getString(stringId: Int): String {
  return resources.getString(stringId)
}