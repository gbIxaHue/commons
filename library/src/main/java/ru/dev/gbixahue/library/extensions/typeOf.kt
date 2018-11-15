package ru.dev.gbixahue.library.extensions

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun stringOf(value: Any?, isNull: String = ""): String {
	if (value == null) return isNull

	return when (value) {
		is String -> value
		is Map<*, *> -> stringOf(value as Map<Any, Any>, isNull = isNull)
		is Iterable<*> -> stringOf(value as Iterable<Any>, isNull = isNull)
		is Array<*> -> stringOf(value as Array<Any>, isNull = isNull)
		is Throwable -> stringOf(value as Throwable)
		else -> value.toString()
	}
}

fun stringOf(map: Map<Any, Any>, isEmpty: String = "{empty}", isNull: String = ""): String {
	if (map.isEmpty()) return isEmpty

	val builder = StringBuilder("{")
	map.forEach {
		val key = stringOf(it.key, isNull)
		val value = stringOf(it.value, isNull)
		builder.append(key).append(":").append(value).append(", ")
	}
	if (builder.length > 1) builder.delete(builder.length - 2, builder.length)

	return builder.append("}").toString()
}

fun stringOf(value: Iterable<Any>, isEmpty: String = "[empty]", isNull: String = ""): String {
	if (!value.iterator().hasNext()) return isEmpty

	val builder = StringBuilder("[")
	value.forEach { builder.append(stringOf(it, isNull)).append(", ") }
	if (builder.length > 1) builder.delete(builder.length - 2, builder.length)

	return builder.append("]").toString()
}

fun stringOf(value: Array<Any>, isEmpty: String = "[empty]", isNull: String = ""): String {
	if (value.isEmpty()) return isEmpty

	val builder = StringBuilder("[")
	value.forEach { builder.append(stringOf(it, isNull)).append(", ") }
	if (builder.length > 1) builder.delete(builder.length - 2, builder.length)

	return builder.append("]").toString()
}

fun stringOf(value: Throwable): String {
	val builder = StringBuilder(value.localizedMessage)
	value.cause?.localizedMessage?.let {
		builder.append(", cause: ").append(it)
	}
	return builder.toString()
}

fun stringOf(value: Boolean): String = if (value) "true" else "false"

fun booleanOf(value: Any?): Boolean {
	if (value == null) return false
	if (value is Boolean) return value
	return true
}