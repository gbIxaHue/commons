package ru.dev.gbixahue.library.extensions.views

import androidx.viewpager.widget.ViewPager

/**
 * Created by Anton Zhilenkov on 24.10.18.
 */

fun androidx.viewpager.widget.ViewPager.addChangeListeners(
		stateChanged: ((Int) -> Unit)? = null,
		scrolled: ((Int, Float, Int) -> Unit)? = null,
		selected: ((Int) -> Unit)? = null
): androidx.viewpager.widget.ViewPager.OnPageChangeListener {

	val listener = object: androidx.viewpager.widget.ViewPager.OnPageChangeListener {
		override fun onPageScrollStateChanged(state: Int) {
			stateChanged?.invoke(state)
		}

		override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
			scrolled?.invoke(position, positionOffset, positionOffsetPixels)
		}

		override fun onPageSelected(position: Int) {
			selected?.invoke(position)
		}
	}
	addOnPageChangeListener(listener)
	return listener
}