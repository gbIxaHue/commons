package ru.dev.gbixahue.library.android.preference.converters

import com.google.gson.Gson

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
open class GsonConverter private constructor(): EntityConverter {

	val gson: Gson = init()

	protected open fun init(): Gson {
		return Gson()
	}

	override fun <T> toString(entity: T): String {
		return gson.toJson(entity)
	}

	override fun <T> fromString(string: String, clazz: Class<T>): T {
		return gson.fromJson(string, clazz)
	}

	companion object {
		val instance: GsonConverter by lazy { GsonConverter() }
	}
}
