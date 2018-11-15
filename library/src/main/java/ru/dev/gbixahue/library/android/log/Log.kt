package ru.dev.gbixahue.library.android.log

import ru.dev.gbixahue.library.extensions.stringOf


/**
 * Created by Anton Zhilenkov on 04.05.17.
 */

object Log {

	private var logger: Logger? = null

	fun setLogger(logger: Logger) {
		Log.logger = logger
	}

	fun d(from: Any, msg: Any?, value: Any? = null) {
		logger?.d(from, stringOf(msg), value)
	}

	fun w(from: Any, msg: Any?, value: Any? = null) {
		logger?.w(from, stringOf(msg), value)
	}

	fun e(from: Any, msg: Any?, value: Any? = null) {
		logger?.e(from, stringOf(msg), value)
	}
}
