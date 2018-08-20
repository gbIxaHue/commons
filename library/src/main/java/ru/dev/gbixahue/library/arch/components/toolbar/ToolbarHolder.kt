package ru.dev.gbixahue.library.arch.components.toolbar

/**
 * Created by Anton Zhilenkov on 17.07.18.
 */
interface ToolbarHolder {
	fun createToolbarManager(builder: ToolbarBuilder?): ToolbarManager?
}