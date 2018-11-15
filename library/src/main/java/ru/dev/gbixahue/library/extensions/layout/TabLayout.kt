package ru.dev.gbixahue.library.extensions.layout

import android.support.design.widget.TabLayout

/**
 * Created by Anton Zhilenkov on 24.10.18.
 */

fun TabLayout.addChangeListener(
		reselected: ((TabLayout.Tab) -> Unit)? = null,
		unselected: ((TabLayout.Tab) -> Unit)? = null,
		selected: ((TabLayout.Tab) -> Unit)? = null
): TabLayout.OnTabSelectedListener {
	val listener = object: TabLayout.OnTabSelectedListener {
		override fun onTabReselected(tab: TabLayout.Tab) {
			reselected?.invoke(tab)
		}

		override fun onTabUnselected(tab: TabLayout.Tab) {
			unselected?.invoke(tab)
		}

		override fun onTabSelected(tab: TabLayout.Tab) {
			selected?.invoke(tab)
		}
	}
	addOnTabSelectedListener(listener)
	return listener
}