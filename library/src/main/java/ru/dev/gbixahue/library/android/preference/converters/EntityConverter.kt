package ru.dev.gbixahue.library.android.preference.converters

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */

interface EntityConverter {

	fun <T> toString(entity: T): String
	fun <T> fromString(string: String, clazz: Class<T>): T
}
