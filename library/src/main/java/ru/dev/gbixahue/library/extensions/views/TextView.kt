package ru.dev.gbixahue.library.extensions.views

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import ru.dev.gbixahue.library.extensions.components.drawableFrom

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun TextView.capsAndUnderline() {
	text = text.toString().toUpperCase()
	paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.setTextFromHtml(htmlString: String) {
	text = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) Html.fromHtml(htmlString) else Html.fromHtml(htmlString, 0)
}

fun TextView.markInvalid(invalid: Boolean, drawableId: Int) {
	setRightDrawable(if (invalid) this.context.drawableFrom(drawableId) else null)
}

fun TextView.setRightDrawable(drawable: Drawable?) {
	if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
		setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
	} else {
		drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
		setCompoundDrawablesRelative(null, null, drawable, null)
	}
}

fun EditText.text() = text.toString().trim()

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit): TextWatcher {
	val watcher = object: TextWatcher {
		override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
		override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

		override fun afterTextChanged(editable: Editable?) {
			afterTextChanged.invoke(editable.toString())
		}
	}
	this.addTextChangedListener(watcher)
	return watcher
}

fun EditText.addInputFilter(filter: InputFilter) {
	val inputFilters = mutableListOf<InputFilter>()
	filters.forEach { inputFilters.add(it) }
	inputFilters.add(filter)
	if (inputFilters.isNotEmpty()) filters = inputFilters.toTypedArray()
}

fun EditText.requestFocusAndMoveCursor(moveCursorToTheEndOfText: Boolean = true) {
	if (isFocused) return
	requestFocus()
	if (text.isEmpty()) return
	if (moveCursorToTheEndOfText) setSelection(text.length)
}

fun EditText.setOnImeActionListener(action: Int, handler: () -> Unit) {
	this.setOnEditorActionListener { view, actionId, event ->
		if (actionId == action) {
			handler.invoke()
			return@setOnEditorActionListener true
		}
		return@setOnEditorActionListener false
	}
}