package ru.dev.gbixahue.library.arch.routing.cicerone.creator_delegate

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import ru.dev.gbixahue.library.arch.routing.Screen

/**
 * Created by Anton Zhilenkov on 16.01.18.
 */
interface CreatorDelegate {
	fun createActivityIntent(context: Context, screenKey: Screen, data: Any?): Intent?
	fun createFragment(screenKey: Screen, data: Any?): Fragment?
	fun exit()

	fun addHandler(handler: Handler)
	fun removeHandler(handler: Handler)
	fun clearHandlers()

	interface Handler {
		fun onActivityChanged(screen: Screen)
		fun onScreenChanged(screen: Screen)
		fun onBack()
	}
}