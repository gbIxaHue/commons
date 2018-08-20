package ru.dev.gbixahue.library.arch.components.toolbar

import android.view.MenuItem
import android.widget.TextView

interface ToolbarManager {
	fun enableMenuItem(id: Int, enable: Boolean)
	fun updateMenuItem(id: Int, configuration: (MenuItem) -> Unit)
	fun updateTitle(id: Int, configuration: (TextView) -> Unit)
	fun setTitle(title: String, subTitle: String? = null)
}