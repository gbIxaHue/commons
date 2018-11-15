package ru.dev.gbixahue.library.arch.mvp.view

import android.view.View
import ru.dev.gbixahue.library.arch.mvp.presenter.MvpPresenter

/**
 * Created by Anton Zhilenkov on 17.09.18.
 */
open class BaseMvpCustomView<in V: MvpView>(
		protected val mainView: View,
		protected val presenter: MvpPresenter<in V>
): MvpCustomView {

	override fun onViewCreated() {
		presenter.created()
		presenter.viewCreated()
	}

	override fun onStart() {
		presenter.started()
	}

	override fun onResume() {
		presenter.resumed()
	}

	override fun onPause() {
		presenter.paused()
	}

	override fun onStop() {
		presenter.stopped()
	}

	override fun onDestroyView() {
		presenter.destroyView()
	}

	override fun onViewDestroyed() {
		presenter.viewDestroyed()
	}

	override fun onDetach() {
		presenter.detached()
	}

	protected fun <T> bindView(id: Int): T {
		return mainView.findViewById<View>(id) as T
	}
}