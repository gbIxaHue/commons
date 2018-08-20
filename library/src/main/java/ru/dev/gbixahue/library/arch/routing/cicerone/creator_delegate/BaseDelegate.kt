package ru.dev.gbixahue.library.arch.routing.cicerone.creator_delegate

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.arch.routing.Screen

/**
 * Created by Anton Zhilenkov on 29.06.18.
 */
abstract class BaseDelegate: CreatorDelegate {

	private val handlers: MutableList<CreatorDelegate.Handler> = mutableListOf()

	override fun createActivityIntent(context: Context, screen: Screen, data: Any?): Intent? {
		Log.d(this, screen)
		val intent = delegateCreateActivityIntent(context, screen, data)
		if (intent != null) handlers.forEach { it.onActivityChanged(screen) }
		return intent
	}

	override fun createFragment(screen: Screen, data: Any?): Fragment? {
		Log.d(this, screen)
		val fragment = delegateCreateFragment(screen, data)
		if (fragment != null) handlers.forEach { it.onScreenChanged(screen) }
		return fragment
	}

	override fun exit() {
		Log.d(this, "exit")
		handlers.forEach { it.onBack() }
		delegateExit()
	}

	override fun addHandler(handler: CreatorDelegate.Handler) {
		handlers.add(handler)
	}

	override fun removeHandler(handler: CreatorDelegate.Handler) {
		handlers.remove(handler)
	}

	override fun clearHandlers() {
		handlers.clear()
	}

	abstract fun delegateCreateActivityIntent(context: Context, screen: Screen, data: Any?): Intent?
	abstract fun delegateCreateFragment(screen: Screen, data: Any?): Fragment?
	abstract fun delegateExit()
}