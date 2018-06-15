package ru.dev.gbixahue.library.android.extensions.views

import android.view.View
import android.widget.ImageView

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun View.enable(enable: Boolean) {
  isEnabled = enable
}

fun View.show(show: Boolean) {
  if (show && visibility == View.VISIBLE) return
  if (! show && visibility == View.GONE) return
  visibility = if (show) View.VISIBLE else View.GONE
}

fun ImageView.show(show: Boolean) {
  if (show && visibility == View.VISIBLE) return
  if (! show && visibility == View.INVISIBLE) return
  visibility = if (show) View.VISIBLE else View.INVISIBLE
}
