package ru.dev.gbixahue.library.tools.transformer.text

import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import ru.dev.gbixahue.library.tools.transformer.Transformer

class TextTransformer(
		private val text: String,
		private val size: Int,
		private val color: Int? = null
): Transformer<Spannable> {

	private val spannable = SpannableString(text)

	override fun transform(): Spannable {
		spannable.setSpan(AbsoluteSizeSpan(size, true), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
		color?.let { spannable.setSpan(ForegroundColorSpan(it), 0, text.length, 0) }
		return spannable
	}
}