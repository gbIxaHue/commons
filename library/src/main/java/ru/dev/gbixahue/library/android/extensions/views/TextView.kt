package ru.dev.gbixahue.library.android.extensions.views

import android.graphics.Paint
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun TextView.capsAndUnderline() {
  text = text.toString().toUpperCase()
  paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.setTextFromHtml(htmlString: String) {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) setText(Html.fromHtml(htmlString)) else setText(Html.fromHtml(htmlString, 0))
}

fun EditText.text() = text.toString().trim()

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
  this.addTextChangedListener(object: TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(editable: Editable?) {
      afterTextChanged.invoke(editable.toString())
    }
  })
}

fun EditText.setOnImeActionListener(action: Int, handler: () -> Unit) {
  this.setOnEditorActionListener({ view, actionId, event ->
    if (actionId == action) {
      handler.invoke()
      return@setOnEditorActionListener true
    }
    return@setOnEditorActionListener false
  })
}