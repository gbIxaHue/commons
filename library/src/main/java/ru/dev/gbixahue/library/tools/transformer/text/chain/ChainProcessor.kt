package ru.dev.gbixahue.library.tools.transformer.text.chain

import ru.dev.gbixahue.library.tools.transformer.Transformer

/**
 * Created by Anton Zhilenkov on 29.10.18.
 *
 * How to use
 *
 * ChainProcessor(
 * 	text,
 * 	SizeDecorator(textSize, 3, text.length -4),
 * 	ColorDecorator(view.colorFrom(R.color.sdk_text_payment_error), 0, text.length / 2)
 * ).transform()
 *
 */
class ChainProcessor<R: CharSequence>(
		private val source: R,
		private vararg val decorators: ChainDecorator<R>
): Transformer<R> {

	private lateinit var result: R

	override fun transform(): R {
		result = source
		decorators.forEach {
			it.setSource(result)
			result = it.transform()
		}

		return result
	}
}