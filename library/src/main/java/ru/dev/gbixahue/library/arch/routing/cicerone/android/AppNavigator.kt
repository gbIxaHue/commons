package ru.dev.gbixahue.library.arch.routing.cicerone.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import ru.dev.gbixahue.library.arch.routing.Screen
import ru.dev.gbixahue.library.arch.routing.cicerone.commands.Command
import ru.dev.gbixahue.library.arch.routing.cicerone.commands.Forward
import ru.dev.gbixahue.library.arch.routing.cicerone.commands.Replace

/**
 * Extends [FragmentNavigator] to allow
 * open new or replace current activity.
 *
 *
 * This navigator DOESN'T provide full featured Activity navigation,
 * but can ease Activity start or replace from current navigator.
 *
 */
abstract class AppNavigator: FragmentNavigator {

	private var activity: AppCompatActivity? = null

	constructor(activity: AppCompatActivity, containerId: Int): super(activity.supportFragmentManager, containerId) {
		this.activity = activity
	}

	constructor(activity: AppCompatActivity, fragmentManager: androidx.fragment.app.FragmentManager, containerId: Int): super(fragmentManager, containerId) {
		this.activity = activity
	}

	/**
	 * Override this method to create option for start activity
	 *
	 * @param command current navigation command. Will be only [Forward] or [Replace]
	 * @param activityIntent activity intent
	 * @return transition options
	 */
	protected fun createStartActivityOptions(command: Command, activityIntent: Intent): Bundle? {
		return null
	}

	override fun forward(command: Forward) {
		val activityIntent = createActivityIntent(activity, command.screenKey, command.transitionData)

		// Start activity
		if (activityIntent != null) {
			val options = createStartActivityOptions(command, activityIntent)
			checkAndStartActivity(command.screenKey, activityIntent, options)
		} else {
			super.forward(command)
		}
	}

	override fun replace(command: Replace) {
		val activityIntent = createActivityIntent(activity, command.screenKey, command.transitionData)

		// Replace activity
		if (activityIntent != null) {
			val options = createStartActivityOptions(command, activityIntent)
			checkAndStartActivity(command.screenKey, activityIntent, options)
			activity !!.finish()
		} else {
			super.replace(command)
		}
	}

	override fun showSystemMessage(message: String) {
		// Toast by default
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
	}

	override fun exit() {
		// Finish by default
		activity !!.finish()
	}

	private fun checkAndStartActivity(screenKey: Screen, activityIntent: Intent, options: Bundle?) {
		// Check if we can start activity
		if (activityIntent.resolveActivity(activity !!.packageManager) != null) {
			activity !!.startActivity(activityIntent, options)
		} else {
			unexistingActivity(screenKey, activityIntent)
		}
	}

	/**
	 * Called when there is no activity to open `screenKey`.
	 *
	 * @param screenKey screen key
	 * @param activityIntent intent passed to start Activity for the `screenKey`
	 */
	protected fun unexistingActivity(screenKey: Screen, activityIntent: Intent) {
		// Do nothing by default
	}

	/**
	 * Creates Intent to start Activity for `screenKey`.
	 *
	 *
	 * **Warning:** This method does not work with [BackTo] command.
	 *
	 *
	 * @param screenKey screen key
	 * @param data initialization data, can be null
	 * @return intent to start Activity for the passed screen key
	 */
	protected abstract fun createActivityIntent(context: Context?, screenKey: Screen, data: Any?): Intent?
}