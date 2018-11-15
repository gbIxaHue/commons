package ru.dev.gbixahue.library.tools.transformer.text.chain

import android.text.SpannableStringBuilder

/**
 * Created by Anton Zhilenkov on 29.10.18.
 */
abstract class BaseChainDecorator<R: CharSequence>(
		protected val start: Int,
		protected val end: Int,
		protected val source: R? = null
): ChainDecorator<R> {

	init {
		if (start < 0) throw IllegalArgumentException("START position can't be lower 0")
		if (end < start) throw IllegalArgumentException("END position can't be lower of START position")
		if (source != null && end > source.length) throw IllegalArgumentException("END position can't be grater than source.LENGTH")
	}

	protected lateinit var chainSource: R

	override fun setSource(chainSource: R) {
		this.chainSource = source ?: chainSource
	}

	protected fun getBuilder(): SpannableStringBuilder {
		if (chainSource is SpannableStringBuilder) return chainSource as SpannableStringBuilder

		return SpannableStringBuilder(chainSource)
	}
}