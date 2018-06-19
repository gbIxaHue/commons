package ru.dev.gbixahue.library.commons.input_widget

/**
 * Created by Anton Zhilenkov on 18.06.18.
 */
interface FloatingAnimator {
	fun updateColor(inFocus: Boolean, isError: Boolean)
	fun show(show: Boolean, callback: (() -> Unit)? = null)
}