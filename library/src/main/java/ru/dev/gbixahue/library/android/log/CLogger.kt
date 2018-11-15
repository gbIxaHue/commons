package ru.dev.gbixahue.library.android.log

import ru.dev.gbixahue.library.android.log.profiling.LogProfiler
import ru.dev.gbixahue.library.extensions.stringOf
import ru.dev.gbixahue.library.hidden_singleton.handler.postWork

/**
 * Created by Anton Zhilenkov on 10.10.17.
 */

open class CLogger(
		private val prefix: String
): Logger {

	protected var logProfiler: LogProfiler? = null
	protected var logHandler: MutableList<LogHandler> = mutableListOf()

	private val builder = StringBuilder()

	override fun d(from: Any, msg: String, value: Any?) {
		print(from, msg, value, LogType.DEBUG)
	}

	override fun w(from: Any, msg: String, value: Any?) {
		print(from, msg, value, LogType.WARNING)
	}

	override fun e(from: Any, msg: String, value: Any?) {
		print(from, msg, value, LogType.ERROR)
	}

	override fun setProfiler(profiler: LogProfiler?) {
		logProfiler = profiler
	}

	override fun addHandler(handler: LogHandler?) {
		handler?.let { logHandler.add(it) }
	}

	protected open fun print(from: Any, msg: String, data: Any?, type: LogType) {
		val logFrom = buildFrom(from)
		val logMessage = buildLogMessage(msg, data)

		postWork { logHandler.forEach { it.handleLog(logFrom + logMessage) } }

		if (logProfiler != null) {
			androidLog(logProfiler!!.profile(from, logMessage), type)
		} else {
			androidLog(logFrom + logMessage, type)
		}
	}

	protected open fun buildLogMessage(msg: String, data: Any?): String {
		if (builder.capacity() > 0) builder.setLength(0)

		builder.append(msg)
		data?.let { builder.append(" {").append(stringOf(it)).append("}") }
		return builder.toString()
	}

	private fun buildFrom(from: Any?): String = "[${getTag(from)}]: "

	private fun androidLog(message: String, type: LogType) {
		when (type) {
			LogType.DEBUG -> android.util.Log.d(prefix, message)
			LogType.WARNING -> android.util.Log.w(prefix, message)
			LogType.ERROR -> android.util.Log.e(prefix, message)
		}
	}

	companion object {
		fun getTag(from: Any?): String {
			if (from == null) return "UNKNOWN"
			return when (from) {
				is CharSequence -> from.toString()
				else -> from.javaClass.simpleName
			}
		}
	}
}
