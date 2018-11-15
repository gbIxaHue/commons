package ru.dev.gbixahue.library.arch.routing.cicerone.android

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import ru.dev.gbixahue.library.arch.routing.Screen
import ru.dev.gbixahue.library.arch.routing.cicerone.Navigator
import ru.dev.gbixahue.library.arch.routing.cicerone.commands.*
import java.util.*

/**
 * [Navigator] implementation based on the fragments.
 *
 *
 * [BackTo] navigation command will return to the root if
 * needed screen isn't found in the screens chain.
 * To change this behavior override [.backToUnexisting] method.
 *
 *
 *
 * [Back] command will call [.exit] method if current screen is the root.
 *
 */
abstract class FragmentNavigator
/**
 * Creates FragmentNavigator.
 *
 * @param fragmentManager fragment manager
 * @param containerId id of the fragments container layout
 */
(private val fragmentManager: FragmentManager, private val containerId: Int): Navigator {

	private var localStackCopy: LinkedList<String> = LinkedList()

	/**
	 * Override this method to setup custom fragment transaction animation.
	 *
	 * @param command current navigation command. Will be only [Forward] or [Replace]
	 * @param currentFragment current fragment in container (for [Replace] command it will be screen previous in new chain, NOT replaced screen)
	 * @param nextFragment next screen fragment
	 * @param fragmentTransaction fragment transaction
	 */
	protected fun setupFragmentTransactionAnimation(
			command: Command,
			currentFragment: Fragment?,
			nextFragment: Fragment,
			fragmentTransaction: FragmentTransaction
	){}

	override fun applyCommands(commands: MutableList<Command>) {
		fragmentManager.executePendingTransactions()

		//copy stack before apply commands
		copyStackToLocal()
		for (command in commands) {
			applyCommand(command)
		}
	}

	private fun copyStackToLocal() {
		localStackCopy.clear()

		val stackSize = fragmentManager.backStackEntryCount
		for (i in 0 until stackSize) {
			localStackCopy.add(fragmentManager.getBackStackEntryAt(i).name)
		}
	}

	/**
	 * Perform mTransition described by the navigation command
	 *
	 * @param command the navigation command to apply
	 */
	protected fun applyCommand(command: Command) {
		when (command) {
			is Forward -> forward(command)
			is Back -> back()
			is Replace -> replace(command)
			is BackTo -> backTo(command)
			is SystemMessage -> showSystemMessage(command.message)
		}
	}

	/**
	 * Performs [Forward] command mTransition
	 */
	protected open fun forward(command: Forward) {
		val fragment = createFragment(command.screenKey, command.transitionData)

		if (fragment == null) {
			unknownScreen(command)
			return
		}

		val fragmentTransaction = fragmentManager.beginTransaction()

		setupFragmentTransactionAnimation(command, fragmentManager.findFragmentById(containerId), fragment, fragmentTransaction)

		fragmentTransaction.replace(containerId, fragment).addToBackStack(command.screenKey.name).commit()
		localStackCopy.add(command.screenKey.name)
	}

	/**
	 * Performs [Forward] command mTransition
	 */
	protected fun back() {
		if (localStackCopy.size > 0) {
			fragmentManager.popBackStack()
			localStackCopy.pop()
		} else {
			exit()
		}
	}

	/**
	 * Performs [Replace] command mTransition
	 */
	protected open fun replace(command: Replace) {
		val fragment = createFragment(command.screenKey, command.transitionData)

		if (fragment == null) {
			unknownScreen(command)
			return
		}

		if (localStackCopy.size > 0) {
			fragmentManager.popBackStack()
			localStackCopy.pop()

			val fragmentTransaction = fragmentManager.beginTransaction()

			setupFragmentTransactionAnimation(command, fragmentManager.findFragmentById(containerId), fragment, fragmentTransaction)

			fragmentTransaction.replace(containerId, fragment).addToBackStack(command.screenKey.name).commit()
			localStackCopy.add(command.screenKey.name)
		} else {
			val fragmentTransaction = fragmentManager.beginTransaction()

			setupFragmentTransactionAnimation(command, fragmentManager.findFragmentById(containerId), fragment, fragmentTransaction)

			fragmentTransaction.replace(containerId, fragment).commit()
		}
	}

	/**
	 * Performs [BackTo] command mTransition
	 */
	protected fun backTo(command: BackTo) {
		if (command.screenKey == null) {
			backToRoot()
			return
		}

		val key = command.screenKey.name
		if (key.isEmpty()) {
			backToRoot()
		} else {
			val index = localStackCopy.indexOf(key)
			val size = localStackCopy.size

			if (index != - 1) {
				for (i in 1 until size - index) {
					localStackCopy.pop()
				}
				fragmentManager.popBackStack(key, 0)
			} else {
				backToUnexisting(command.screenKey.name)
			}
		}
	}

	private fun backToRoot() {
		fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
		localStackCopy.clear()
	}

	/**
	 * Creates Fragment matching `screenKey`.
	 *
	 * @param screenKey screen key
	 * @param data initialization data
	 * @return instantiated fragment for the passed screen key
	 */
	protected abstract fun createFragment(screenKey: Screen, data: Any?): Fragment?

	/**
	 * Shows system message.
	 *
	 * @param message message to show
	 */
	protected abstract fun showSystemMessage(message: String)

	/**
	 * Called when we try to back from the root.
	 */
	protected abstract fun exit()

	/**
	 * Called when we tried to back to some specific screen (via [BackTo] command),
	 * but didn't found it.
	 *
	 * @param screenKey screen key
	 */
	protected fun backToUnexisting(screenKey: String) {
		backToRoot()
	}

	/**
	 * Called if we can't create a screen.
	 */
	protected fun unknownScreen(command: Command) {
		throw RuntimeException("Can't create a screen for passed screenKey.")
	}
}
