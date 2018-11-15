package ru.dev.gbixahue.library.tools.transformer.text

import android.text.SpannableStringBuilder
import ru.dev.gbixahue.library.tools.transformer.Transformer

class SplitTextTransformer(
	private val first: TextTransformer,
	private val second: TextTransformer,
	private val delimiter: String,
	private val withSpaces: Boolean = true
): Transformer<CharSequence> {

	override fun transform(): CharSequence {
		val mergeWithDelimiter = if (withSpaces) " $delimiter " else delimiter
		val builder = SpannableStringBuilder()
		builder.append(first.transform())
				.append(mergeWithDelimiter)
				.append(second.transform())
		return builder
	}
}