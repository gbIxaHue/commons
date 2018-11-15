package ru.dev.gbixahue.library.tools.transformer.text.chain

import ru.dev.gbixahue.library.tools.transformer.Transformer

/**
 * Created by Anton Zhilenkov on 29.10.18.
 */
interface ChainDecorator<R>: Transformer<R> {
	fun setSource(chainSource: R)
}