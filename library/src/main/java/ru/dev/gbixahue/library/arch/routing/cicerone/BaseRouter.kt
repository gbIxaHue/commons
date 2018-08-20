package ru.dev.gbixahue.library.arch.routing.cicerone

import ru.dev.gbixahue.library.arch.routing.cicerone.commands.Command

/**
 * BaseRouter is an abstract class to implement high-level navigation.
 * Extend it to add needed transition methods.
 */
abstract class BaseRouter {
	internal val commandBuffer: CommandBuffer

	init {
		this.commandBuffer = CommandBuffer()
	}

	/**
	 * Sends navigation command array to [CommandBuffer].
	 *
	 * @param commands navigation command array to execute
	 */
	protected fun executeCommands(vararg commands: Command) {
		commandBuffer.executeCommands(commands.toMutableList())
	}
}
