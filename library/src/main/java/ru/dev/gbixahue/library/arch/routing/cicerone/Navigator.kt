package ru.dev.gbixahue.library.arch.routing.cicerone

import ru.dev.gbixahue.library.arch.routing.cicerone.commands.Command

/**
 * The low-level navigation interface.
 * Navigator is the one who actually performs any transition.
 */
interface Navigator {

	/**
	 * Performs transition described by the navigation command
	 *
	 * @param commands the navigation command array to apply per single transaction
	 */
	fun applyCommands(commands: MutableList<Command>)
}
