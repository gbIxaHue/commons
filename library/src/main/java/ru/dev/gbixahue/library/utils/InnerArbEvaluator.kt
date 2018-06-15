package ru.dev.gbixahue.library.utils

import android.animation.TypeEvaluator

/**
 * Created by Anton Zhilenkov on 15.06.18.
 */
class InnerArbEvaluator: TypeEvaluator<Int> {

	override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
		val startA = startValue shr 24 and 0xff
		val startR = startValue shr 16 and 0xff
		val startG = startValue shr 8 and 0xff
		val startB = startValue and 0xff

		val endA = endValue shr 24 and 0xff
		val endR = endValue shr 16 and 0xff
		val endG = endValue shr 8 and 0xff
		val endB = endValue and 0xff

		return ((startA + (fraction * (endA - startA)).toInt() shl 24)
				or (startR + (fraction * (endR - startR)).toInt() shl 16)
				or (startG + (fraction * (endG - startG)).toInt() shl 8)
				or (startB + (fraction * (endB - startB)).toInt()))
	}
}