package ru.dev.gbixahue.library.extensions.components

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun AppCompatActivity.closeKeyboard() {
	val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	if (currentFocus != null) {
		imm.hideSoftInputFromWindow(currentFocus !!.windowToken, 0)
	}
}

fun AppCompatActivity.openKeyboard() {
	val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputMethodManager.toggleSoftInputFromWindow(window.decorView.rootView.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
}