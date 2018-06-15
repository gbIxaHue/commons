package ru.dev.gbixahue.library.android.locale

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.support.v7.app.AppCompatActivity
import ru.dev.gbixahue.library.android.preference.Key
import ru.dev.gbixahue.library.android.preference.PreferenceRepository
import java.util.*

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 * Create an array of strings and put name of the support language and locale identifier after delimiter "|".
 * E.g. <item>Русский|ru</item>
 */
class LocaleLanguageSwitcher(private val context: Context, private val prefRepository: PreferenceRepository) {

  private var deviceLocale = Locale.getDefault()

  fun changeAppLanguage(userChoice: String) {
    if (deviceLocale.language == userChoice) return
    prefRepository.save(Key.USER_LANGUAGE, userChoice)
    deviceLocale = Locale(userChoice)
    updateResources(context, userChoice)
  }

  fun restoreUserChoice() {
    if (! prefRepository.has(Key.USER_LANGUAGE)) return
    val restoredUserChoice = getUserChoiceLanguage()
    deviceLocale = Locale(restoredUserChoice)
    updateResources(context, restoredUserChoice)
  }

  fun attachBaseContext(context: Context): Context {
    return updateResources(context, getUserChoiceLanguage())
  }

  fun resetTitles(activity: AppCompatActivity) {
    try {
      val info = context.packageManager.getActivityInfo(activity.componentName, GET_META_DATA)
      if (info.labelRes != 0) {
        activity.setTitle(info.labelRes)
      }
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
    }
  }

  fun getLocale(res: Resources): Locale {
    val config = res.configuration
    return if (Build.VERSION.SDK_INT >= 24) config.locales.get(0) else config.locale
  }

  private fun getUserChoiceLanguage(): String {
    return prefRepository.restore(Key.USER_LANGUAGE, deviceLocale.language)
  }

  private fun updateResources(context: Context, language: String): Context {
    var context = context
    val locale = Locale(language)
    Locale.setDefault(locale)

    val res = context.resources
    val config = Configuration(res.configuration)
    if (Build.VERSION.SDK_INT >= 17) {
      config.setLocale(locale)
      context = context.createConfigurationContext(config)
    } else {
      config.locale = locale
      res.updateConfiguration(config, res.displayMetrics)
    }
    return context
  }
}