package ru.dev.gbixahue.library.extensions.views

import android.view.View
import android.widget.ImageView

/**
 * Created by Anton Zhilenkov on 15.11.18.
 */
fun ImageView.show(show: Boolean) {
	if (show && visibility == View.VISIBLE) return
	if (!show && visibility == View.INVISIBLE) return
	visibility = if (show) View.VISIBLE else View.INVISIBLE
}