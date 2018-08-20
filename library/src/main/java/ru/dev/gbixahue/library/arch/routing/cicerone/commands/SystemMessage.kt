package ru.dev.gbixahue.library.arch.routing.cicerone.commands

/**
 * Shows system message.
 *
 * Creates a [SystemMessage] command.
 * @param message message text
 */
class SystemMessage(val message: String): Command
