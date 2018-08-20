package ru.dev.gbixahue.library.arch.components.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.dev.gbixahue.library.android.LayoutHolder

abstract class BaseFragment: Fragment(), LayoutHolder {

	companion object {
		const val A_DATA = "arguments_data"
		const val A_DATA_ITEM_ID = "arguments_data_item_id"
	}

	protected lateinit var mainView: View

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		mainView = inflater.inflate(getLayoutId(), container, false)
		return mainView
	}
}