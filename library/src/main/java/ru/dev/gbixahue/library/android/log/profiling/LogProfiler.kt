package ru.dev.gbixahue.library.android.log.profiling

/**
 * Created by Anton Zhilenkov on 03.07.18.
 */
interface LogProfiler {
	fun profile(from: Any, msg: String): String
}