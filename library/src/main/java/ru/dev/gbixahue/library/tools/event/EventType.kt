package ru.dev.gbixahue.library.tools.event

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 * How to use: create an enum class extending from this interface and use constants in order to distinguish different types of events
 */
interface EventType {
	val name: String
}