package ru.dev.gbixahue.library.android

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import ru.dev.gbixahue.library.android.locale.LocaleLanguageSwitcher
import ru.dev.gbixahue.library.android.log.CLogger
import ru.dev.gbixahue.library.android.log.profiling.CLogProfiler
import ru.dev.gbixahue.library.android.preference.PreferenceRepository
import ru.dev.gbixahue.library.android.preference.converters.GsonConverter
import ru.dev.gbixahue.library.tools.predictable.LaunchFactory

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
abstract class BaseApplication: Application() {

	lateinit var prefRepo: PreferenceRepository
	lateinit var languageSwitcher: LocaleLanguageSwitcher

	private val launchFactory: LaunchFactory = LaunchFactory()

	override fun attachBaseContext(base: Context) {
		prefRepo = PreferenceRepository(base.getSharedPreferences(getPreferenceName(base), Context.MODE_PRIVATE), GsonConverter.instance)
		languageSwitcher = LocaleLanguageSwitcher(base, prefRepo)
		super.attachBaseContext(languageSwitcher.attachBaseContext(base))
	}

	protected open fun getPreferenceName(base: Context): String = base.packageName

	override fun onCreate() {
		super.onCreate()
		initLogger()
		initLaunchPerformer()
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)
		languageSwitcher.restoreUserChoice()
	}

	private fun initLaunchPerformer() {
		configureLaunchFactory(launchFactory)
		launchFactory.execute()
	}


	private fun initLogger() {
		Log.d("TAG", "initLogger")
		if (isDebug()) ru.dev.gbixahue.library.android.log.Log.setLogger(CLogger(getLogName()).apply { setProfiler(CLogProfiler()) })
	}

	abstract fun configureLaunchFactory(launchFactory: LaunchFactory)
	abstract fun isDebug(): Boolean
	abstract fun getLogName(): String
}