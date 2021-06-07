package ru.dev.gbixahue.library.android.view.view_pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by Anton Zhilenkov on 23.05.17.
 */

abstract class BaseViewPagerAdapter<D>(fm: FragmentManager, private val fragmentClass: Class<out Fragment>): FragmentStatePagerAdapter(fm) {

	protected var itemsData: MutableList<D> = mutableListOf()
	protected var titles: MutableList<String> = mutableListOf()

	fun setItems(data: MutableList<D>) {
		itemsData.clear()
		itemsData.addAll(data)
	}

	fun setItemsTitle(titles: MutableList<String>) {
		this.titles = titles
	}

	override fun getItem(position: Int): Fragment {
			val fragmentItem = fragmentClass.newInstance()
			if (itemsData.size > 0) {
				val bundle = getArguments(itemsData[position])
				if (bundle != null) fragmentItem.arguments = bundle
			}
			return fragmentItem
	}

	override fun getCount(): Int = itemsData.size

	override fun getPageTitle(position: Int): CharSequence? {
		return titles[position]
	}

	abstract fun getArguments(itemData: D): Bundle?
}
