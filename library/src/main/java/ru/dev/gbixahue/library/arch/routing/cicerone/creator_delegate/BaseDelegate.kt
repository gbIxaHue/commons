package ru.dev.gbixahue.library.arch.routing.cicerone.creator_delegate

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.arch.routing.Screen

/**
 * Created by Anton Zhilenkov on 29.06.18.
 */
abstract class BaseDelegate: CreatorDelegate {

	private val handlers: MutableList<CreatorDelegate.Handler> = mutableListOf()

	override fun createActivityIntent(context: Context, screenKey: Screen, data: Any?): Intent? {
		Log.d(this, screenKey)
		val intent = delegateCreateActivityIntent(context, screenKey, data)
		if (intent != null) handlers.forEach { it.onActivityChanged(screenKey) }
		return intent
	}

	override fun createFragment(screenKey: Screen, data: Any?): androidx.fragment.app.Fragment? {
		Log.d(this, screenKey)
		val fragment = delegateCreateFragment(screenKey, data)
		if (fragment != null) handlers.forEach { it.onScreenChanged(screenKey) }
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
	abstract fun delegateCreateFragment(screen: Screen, data: Any?): androidx.fragment.app.Fragment?
	abstract fun delegateExit()
}