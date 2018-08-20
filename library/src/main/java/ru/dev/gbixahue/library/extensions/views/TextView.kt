package ru.dev.gbixahue.library.extensions.views

import android.animation.ValueAnimator
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import ru.dev.gbixahue.library.utils.InnerArbEvaluator

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun TextView.capsAndUnderline() {
	text = text.toString().toUpperCase()
	paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.setTextFromHtml(htmlString: String) {
	if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) setText(Html.fromHtml(htmlString)) else setText(Html.fromHtml(htmlString, 0))
}

fun TextView.setRightDrawable(drawable: Drawable?) {
	setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
}

fun TextView.animateColorChanges(to: Int, from: Int, duration: Long = 300) {
	ValueAnimator.ofObject(InnerArbEvaluator(), from, to).apply {
		setDuration(duration)
		addUpdateListener { setTextColor(it.animatedValue as Int) }
	}.start()
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