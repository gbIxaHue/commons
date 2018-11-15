package ru.dev.gbixahue.library.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan

/**
 * Created by Anton Zhilenkov on 12.12.17.
 */
fun String.coloriseByHtml(hexColor: String): String = "<font color=$hexColor>$this</font>"

fun String.insertAt(position: Int, sequence: String): String {
	if (sequence.isEmpty() || (position < 0 || position > length)) return this

	return substring(0, position) + sequence + subSequence(position, length)
}

fun String.appendBitmap(bitmap: Bitmap): Spannable {
	val stringValue = if (isEmpty()) " " else this
	val starSpan = object: ImageSpan(bitmap, ImageSpan.ALIGN_BOTTOM) {
		override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
			canvas.save()
			val paddingTop = bottom / 2 - drawable.minimumHeight / 2
			canvas.translate(x, paddingTop.toFloat())
			drawable.draw(canvas)
			canvas.restore()
		}
	}
	val stringBuilder = SpannableStringBuilder(stringValue)
	stringBuilder.setSpan(starSpan, stringValue.length - 1, stringValue.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
	return stringBuilder
}