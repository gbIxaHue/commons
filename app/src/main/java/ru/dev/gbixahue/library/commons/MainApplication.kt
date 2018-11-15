package ru.dev.gbixahue.library.commons

import ru.dev.gbixahue.library.android.BaseApplication
import ru.dev.gbixahue.library.tools.predictable.LaunchFactory

/**
 * Created by Anton Zhilenkov on 15.11.18.
 */
class MainApplication: BaseApplication() {

	override fun configureLaunchFactory(launchFactory: LaunchFactory) {
	}

	override fun isDebug(): Boolean {
		return BuildConfig.DEBUG
	}

	override fun getLogName(): String {
		return "Main"
	}
}