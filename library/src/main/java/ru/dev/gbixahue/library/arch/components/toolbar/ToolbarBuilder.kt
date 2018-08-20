package ru.dev.gbixahue.library.arch.components.toolbar

import android.content.res.ColorStateList
import android.os.Build
import android.support.annotation.IdRes
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.extensions.components.stringFrom

class ToolbarBuilder {

	var toolbarId: Int = - 1
	var title: CharSequence = ""
	var titleId: Int = - 1
	var subTitle: CharSequence? = null
	var subTitleId: Int = - 1
	var menuId: Int = - 1
	var menuItemsColor: Int = - 1
	var showNavigationBackArrow: Boolean = false
	var onNavigationBackClickListener: (() -> Unit)? = null
	var elevation: Float = - 1f

	private var menu: Menu? = null
	private val menuItemCallbacks: MutableList<Triple<Int, () -> Unit, Boolean>> = mutableListOf()

	private var searchViewId: Int = - 1
	private var setupFunction: ((searchView: SearchView) -> Unit)? = null

	fun withMenuItems(@IdRes menuItemId: Int, clickHandler: () -> Unit, enabled: Boolean = true) {
		menuItemCallbacks.add(Triple(menuItemId, clickHandler, enabled))
	}

	fun findMenuItem(@IdRes menuItemId: Int): MenuItem? = menu?.findItem(menuItemId)

	companion object {
		const val NO_TOOLBAR = - 1

		fun prepareToolbar(builder: ToolbarBuilder, container: View): ToolbarManager? {
			if (builder.toolbarId == NO_TOOLBAR) {
				Log.e(builder, "Toolbar getToolbarBuilder try initiated with NO_TOOLBAR id")
				return null
			}
			val toolbar = container.findViewById(builder.toolbarId) as? Toolbar ?: return null
			val toolbarManager: ToolbarManager = ToolbarManagerImpl(toolbar)
			prepareTitles(builder, toolbar)
			prepareMenu(toolbarManager, builder, toolbar)
			prepareSearch(builder, toolbar)
			prepareBackButton(builder, toolbar)
			prepareElevation(builder, toolbar)
			return toolbarManager
		}

		private fun prepareElevation(builder: ToolbarBuilder, it: Toolbar) {
			if (builder.elevation != - 1f && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				it.elevation = builder.elevation
			}
		}

		private fun prepareTitles(builder: ToolbarBuilder, toolbar: Toolbar) {
			toolbar.title = if (builder.titleId != - 1) toolbar.context.stringFrom(builder.titleId) else builder.title
			toolbar.subtitle = if (builder.subTitleId != - 1) toolbar.context.stringFrom(builder.subTitleId) else builder.subTitle
		}

		private fun prepareMenu(manager: ToolbarManager, builder: ToolbarBuilder, toolbar: Toolbar) {
			if (builder.menuId != - 1) {
				toolbar.inflateMenu(builder.menuId)
			}

			if (builder.menuItemsColor != - 1) {
				builder.menu = toolbar.menu
				for (index in 0 until toolbar.menu.size()) {
					MenuItemCompat.setIconTintList(toolbar.menu.getItem(index),
							ColorStateList.valueOf(toolbar.resources.getColor(builder.menuItemsColor)))
				}
			}

			builder.menuItemCallbacks.forEach { tripleIdCallbackState ->
				manager.updateMenuItem(tripleIdCallbackState.first) { menuItem ->
					menuItem.setOnMenuItemClickListener {
						tripleIdCallbackState.second.invoke()
						true
					}
				}
				manager.enableMenuItem(tripleIdCallbackState.first, tripleIdCallbackState.third)
			}
		}

		private fun prepareSearch(builder: ToolbarBuilder, toolbar: Toolbar) {
			if (builder.searchViewId == - 1) return
			val menu = toolbar.menu ?: return
			(menu.findItem(builder.searchViewId)?.actionView as? SearchView)?.let { searchItem ->
				builder.setupFunction?.invoke(searchItem)
			}
		}

		private fun prepareBackButton(builder: ToolbarBuilder, toolbar: Toolbar) {
			if (! builder.showNavigationBackArrow) return
			toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material)
			builder.onNavigationBackClickListener?.let { callback -> toolbar.setNavigationOnClickListener { callback.invoke() } }
		}
	}
}

