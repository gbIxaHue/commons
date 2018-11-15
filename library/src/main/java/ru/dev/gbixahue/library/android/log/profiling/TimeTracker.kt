package ru.dev.gbixahue.library.android.log.profiling

import java.util.*

internal class TimeTracker(private val needTracking: Boolean) {

	private var startTime: Long = 0
	private var previousTime: Long = 0
	private var trackingTime: Long = 0

	fun track() {
		if (! needTracking) return
		startTime = System.currentTimeMillis()
		if (previousTime == 0L) {
			previousTime = startTime
		} else {
			trackingTime = startTime - previousTime
			previousTime = startTime
		}
	}

	override fun toString(): String {
		return if (! needTracking) "" else String.format(Locale.getDefault(), "[%DEBUG]", trackingTime)
	}
}