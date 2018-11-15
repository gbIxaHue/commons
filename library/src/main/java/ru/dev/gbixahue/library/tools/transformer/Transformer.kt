package ru.dev.gbixahue.library.tools.transformer

/**
 * Created by Anton Zhilenkov on 29.03.18.
 */
interface Transformer<R> {
	fun transform(): R
}