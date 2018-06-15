package ru.dev.gbixahue.library.android.extensions.components

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun AppCompatActivity.showToast(messageResId: Int) {
  showToast(resources.getString(messageResId))
}

fun AppCompatActivity.showToast(message: String) {
  if (message.isBlank()) return
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.colorizeStatusBar(@ColorRes color: Int) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, color)
  }
}

fun AppCompatActivity.closeKeyboard() {
  val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  if (currentFocus != null) {
    imm.hideSoftInputFromWindow(currentFocus !!.windowToken, 0)
  }
}

fun AppCompatActivity.openKeyboard() {
  window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}