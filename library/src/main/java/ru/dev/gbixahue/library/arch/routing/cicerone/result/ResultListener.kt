package ru.dev.gbixahue.library.arch.routing.cicerone.result

interface ResultListener {

	/**
	 * Received result from screen.
	 */
	fun onResult(resultData: Any)
}
