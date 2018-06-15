package ru.dev.gbixahue.library.extensions.components

import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun Fragment.showToast(messageResId: Int) {
	showToast(resources.getString(messageResId))
}

fun Fragment.showToast(message: String) {
	Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}