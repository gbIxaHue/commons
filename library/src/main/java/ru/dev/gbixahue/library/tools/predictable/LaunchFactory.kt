package ru.dev.gbixahue.library.tools.predictable

import ru.dev.gbixahue.library.hidden_singleton.handler.postWork

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class LaunchFactory {

	val mainThreadPredictable: MutableList<PredictableAction> = mutableListOf()
	val workThreadPredictable: MutableList<PredictableAction> = mutableListOf()

	fun execute() {
		mainThreadPredictable.forEach { if (it.performExpression()) it.performAction() }
		postWork { workThreadPredictable.forEach { if (it.performExpression()) it.performAction() } }
	}
}