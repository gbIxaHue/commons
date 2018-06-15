package ru.dev.gbixahue.library.predictable

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface Predicate {
  fun performExpression(): Boolean
}

interface PredictableAction: Predicate {
  fun performAction()
}

interface Predictable {
  val predicate: Predicate
}