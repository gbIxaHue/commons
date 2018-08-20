package ru.dev.gbixahue.library.arch.mvp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import ru.dev.gbixahue.library.R
import ru.dev.gbixahue.library.arch.OnBackPressHandler
import ru.dev.gbixahue.library.arch.components.fragment.BaseFragment
import ru.dev.gbixahue.library.arch.mvp.presenter.MvpPresenter
import ru.dev.gbixahue.library.arch.routing.RouterHolder
import ru.dev.gbixahue.library.arch.routing.RouterProvider
import ru.dev.gbixahue.library.arch.routing.cicerone.Router
import ru.dev.gbixahue.library.extensions.views.colorFrom

/**
 * Created by Anton Zhilenkov on 29.06.18.
 */
abstract class BaseMvpFragment: BaseFragment(), MvpView, RouterProvider, OnBackPressHandler {

	protected var presenter: MvpPresenter<in MvpView>? = null

	override fun getRootRouter(): Router? = (parentFragment as? RouterProvider)?.getRootRouter()
	override fun getBaseRouter(): Router? = (parentFragment as? RouterProvider)?.getBaseRouter()

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		initComponent()
		presenter = getMvpPresenter()
		presenter?.attached(this)
		(presenter as? RouterHolder)?.let { holder ->
			(parentFragment as? RouterProvider)?.getRootRouter()?.let { holder.attachRouter(it) }
		}
	}

	protected abstract fun initComponent()
	protected abstract fun getMvpPresenter(): MvpPresenter<in MvpView>?

	protected open fun fragmentIsReplaced(): Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		presenter?.created()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		val view = super.onCreateView(inflater, container, savedInstanceState)

		if (! fragmentIsReplaced()) {
			mainView.isClickable = true
			mainView.setBackgroundColor(mainView.colorFrom(R.color.appWindowBackground))
		}

		presenter?.createView()
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		presenter?.viewCreated()
	}

	override fun onStart() {
		super.onStart()
		presenter?.started()
	}

	override fun onResume() {
		super.onResume()
		presenter?.resumed()
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> onBackPressed()
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onBackPressed(): Boolean = (presenter as? OnBackPressHandler)?.onBackPressed() ?: false

	override fun onPause() {
		super.onPause()
		presenter?.paused()
	}

	override fun onStop() {
		super.onStop()
		presenter?.stopped()
	}

	override fun onDestroyView() {
		presenter?.destroyView()
		super.onDestroyView()
		presenter?.viewDestroyed()
	}

	override fun onDetach() {
		super.onDetach()
		presenter?.detached()
	}
}