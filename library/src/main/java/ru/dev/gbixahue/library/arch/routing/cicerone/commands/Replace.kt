package ru.dev.gbixahue.library.arch.routing.cicerone.commands

import ru.dev.gbixahue.library.arch.routing.Screen

/**
 * Replaces the current screen.
 *
 * Creates a [Replace] navigation command.
 * @param screenKey      screen key
 * @param transitionData initial data
 */
class Replace(val screenKey: Screen, val transitionData: Any? = null): Command
