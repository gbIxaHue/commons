package ru.dev.gbixahue.library.arch.routing.cicerone.commands

import ru.dev.gbixahue.library.arch.routing.Screen

/**
 * Opens new screen.
 * * Creates a [Forward] navigation command.
 * @param screenKey      screen key
 * @param transitionData initial data
 */
class Forward(val screenKey: Screen, val transitionData: Any?): Command