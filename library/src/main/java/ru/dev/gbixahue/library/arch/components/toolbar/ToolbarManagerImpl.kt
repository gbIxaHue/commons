package ru.dev.gbixahue.library.arch.components.toolbar

import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView

class ToolbarManagerImpl(private val toolbar: Toolbar): ToolbarManager {

	override fun enableMenuItem(id: Int, enable: Boolean) {
		toolbar.menu.findItem(id)?.let { enableMenuItem(it, enable) }
	}

	override fun updateMenuItem(id: Int, configuration: (MenuItem) -> Unit) {
		toolbar.menu.findItem(id)?.let { configuration.invoke(it) }
	}

	override fun updateTitle(id: Int, configuration: (TextView) -> Unit) {
		toolbar.findViewById<TextView>(id)?.let { configuration.invoke(it) }
	}

	override fun setTitle(title: String, subTitle: String?) {
		toolbar.title = title
		toolbar.subtitle = subTitle
	}

	companion object {

		private fun enableMenuItem(menuItem: MenuItem, enable: Boolean) {
			menuItem.isEnabled = enable
			menuItem.icon.alpha = if (enable) 255 else 64
		}
	}
}