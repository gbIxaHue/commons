package ru.dev.gbixahue.library.utils

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun stringOf(value: Any?): String {
	if (value == null) return ""
	if (value is String) return value
	return value.toString()
}

fun booleanOf(value: Any?): Boolean {
	if (value == null) return false
	if (value is Boolean) return value
	return true
}