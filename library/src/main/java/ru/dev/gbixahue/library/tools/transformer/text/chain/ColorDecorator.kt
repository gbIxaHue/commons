package ru.dev.gbixahue.library.tools.transformer.text.chain

import androidx.annotation.ColorInt
import android.text.style.ForegroundColorSpan

/**
 * Created by Anton Zhilenkov on 29.10.18.
 */
class ColorDecorator(
		@ColorInt private val color: Int,
		start: Int,
		end: Int,
		source: CharSequence? = null
): BaseChainDecorator<CharSequence>(start, end, source) {

	override fun transform(): CharSequence {
		val builder = getBuilder()
		builder.setSpan(ForegroundColorSpan(color), start, end, 0)
		return builder
	}
}