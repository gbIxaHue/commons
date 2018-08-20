package ru.dev.gbixahue.library.android.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.dev.gbixahue.library.android.BaseApplication
import ru.dev.gbixahue.library.android.locale.LocaleLanguageSwitcher
import ru.dev.gbixahue.library.tools.analysis.ATrack
import ru.dev.gbixahue.library.tools.analysis.event.TrackerEvent

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
open class BaseActivity: AppCompatActivity() {

	protected val tracker: ATrack = ATrack
	protected lateinit var languageSwitcher: LocaleLanguageSwitcher

	override fun attachBaseContext(base: Context) {
		languageSwitcher = (base.applicationContext as BaseApplication).languageSwitcher
		super.attachBaseContext(languageSwitcher.attachBaseContext(base))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		languageSwitcher = (applicationContext as BaseApplication).languageSwitcher
		super.onCreate(savedInstanceState)
		languageSwitcher.restoreUserChoice()
		languageSwitcher.resetTitles(this)
	}

	protected open fun sendOpenScreenEvent(screeName: String, values: MutableMap<String, String>) {
		tracker.send(TrackerEvent("Screen", screeName).withValues(values))
	}
}