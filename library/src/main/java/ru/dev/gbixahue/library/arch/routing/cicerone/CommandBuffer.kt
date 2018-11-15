package ru.dev.gbixahue.library.arch.routing.cicerone

import ru.dev.gbixahue.library.arch.routing.cicerone.commands.Command
import java.util.*

/**
 * Passes navigation command to an active [Navigator]
 * or stores it in the pending commands queue to pass it later.
 */
internal class CommandBuffer: NavigatorHolder {
	private var navigator: Navigator? = null
	private val pendingCommands = LinkedList<MutableList<Command>>()

	override fun setNavigator(navigator: Navigator) {
		this.navigator = navigator
		while (! pendingCommands.isEmpty()) {
			if (this.navigator != null) {
				executeCommands(pendingCommands.poll())
			} else
				break
		}
	}

	override fun removeNavigator() {
		this.navigator = null
	}

	/**
	 * Passes `commands` to the [Navigator] if it available.
	 * Else puts it to the pending commands queue to pass it later.
	 * @param commands navigation command array
	 */
	fun executeCommands(commands: MutableList<Command>) {
		if (navigator != null) {
			navigator?.applyCommands(commands)
		} else {
			pendingCommands.add(commands)
		}
	}
}
