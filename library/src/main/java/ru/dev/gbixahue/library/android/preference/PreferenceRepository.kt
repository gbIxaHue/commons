package ru.dev.gbixahue.library.android.preference

import android.content.SharedPreferences
import com.google.gson.JsonSyntaxException
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.android.preference.converters.EntityConverter


/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class PreferenceRepository(private val sharedPreferences: SharedPreferences, private val converter: EntityConverter) {

  private val editor: SharedPreferences.Editor = sharedPreferences.edit()

  fun <T> save(preferenceKey: PreferenceKey, data: T): T = save(getKey(preferenceKey), data)
  fun <T> save(key: String, data: T): T {
    when (data) {
      is Boolean -> editor.putBoolean(key, (data as Boolean?) !!)
      is String -> editor.putString(key, data as String?)
      is Int -> editor.putInt(key, (data as Int?) !!)
      is Long -> editor.putLong(key, (data as Long?) !!)
      is Float -> editor.putFloat(key, (data as Float?) !!)
      else -> editor.putString(key, converter.toString(data))
    }
    editor.commit()
    return data
  }

  fun <T: Any> restore(preferenceKey: PreferenceKey, defaultValue: T): T = restore(getKey(preferenceKey), defaultValue)
  fun <T: Any> restore(key: String, defaultValue: T): T {
    if (! sharedPreferences.contains(key)) return defaultValue
    return try {
      when (defaultValue) {
        is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
        is String -> sharedPreferences.getString(key, defaultValue) as T
        is Int -> sharedPreferences.getInt(key, defaultValue) as T
        is Long -> sharedPreferences.getLong(key, defaultValue) as T
        is Float -> sharedPreferences.getFloat(key, defaultValue) as T
        else -> {
          val jsonString = sharedPreferences.getString(key, null) ?: return defaultValue
          converter.fromString(jsonString, defaultValue::class.java)
        }
      }
    } catch (ex: JsonSyntaxException) {
      Log.e(this, ex)
      defaultValue
    }
  }

  fun has(preferenceKey: PreferenceKey): Boolean = has(getKey(preferenceKey))
  fun has(key: String): Boolean = sharedPreferences.contains(key)

  fun remove(preferenceKey: PreferenceKey) {
    remove(getKey(preferenceKey))
  }

  fun remove(key: String) {
    editor.remove(key).commit()
  }

  fun clear() {
    editor.clear().commit()
  }

  private fun getKey(preferenceKey: PreferenceKey) = preferenceKey.toString().toLowerCase()
}
