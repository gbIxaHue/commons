package ru.dev.gbixahue.library.extensions.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import ru.dev.gbixahue.library.hidden_singleton.handler.postUI

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun Activity.safeStartActivity(intent: Intent, failureCallback: (() -> Unit)? = null) {
	if (intent.resolveActivity(packageManager) != null) {
		postUI {
			ContextCompat.startActivity(this, intent, null)
		}
	} else {
		failureCallback?.invoke()
	}
}

fun Activity.closeKeyboard() {
	val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	if (currentFocus != null) {
		imm.hideSoftInputFromWindow(currentFocus !!.windowToken, 0)
	}
}

fun Activity.openKeyboard() {
	val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputMethodManager.toggleSoftInputFromWindow(window.decorView.rootView.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
}