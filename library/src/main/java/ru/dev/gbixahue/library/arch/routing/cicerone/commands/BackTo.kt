package ru.dev.gbixahue.library.arch.routing.cicerone.commands

import ru.dev.gbixahue.library.arch.routing.Screen

/**
 * Rolls back to the needed screen from the screens chain.
 * Behavior in the case when no needed screens found depends on an implementation of the [Navigator].
 * But the recommended behavior is to return to the root.
 *
 * Creates a [BackTo] navigation command.
 * @param screenKey screen key
 */
class BackTo(val screenKey: Screen? = null): Command
