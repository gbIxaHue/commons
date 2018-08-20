package ru.dev.gbixahue.library.arch.mvp.presenter

import io.reactivex.disposables.CompositeDisposable
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.arch.OnBackPressHandler
import ru.dev.gbixahue.library.arch.mvp.view.MvpView
import ru.dev.gbixahue.library.arch.routing.RouterHolder
import ru.dev.gbixahue.library.arch.routing.cicerone.Router

/**
 * Created by Anton Zhilenkov on 03.07.17.
 */

abstract class BaseMvpPresenter<View: MvpView>: MvpPresenter<View>, RouterHolder, OnBackPressHandler {

	protected var view: View? = null
	protected var router: Router? = null

	protected val pauseDisposable = CompositeDisposable()
	protected val stopDisposable = CompositeDisposable()
	protected val destroyDisposable = CompositeDisposable()

	override fun attachRouter(router: Router) {
		this.router = router
	}

	override fun attached(view: View) {
		Log.d(this, "attached")
		this.view = view
	}

	override fun created() {
		Log.d(this, "created")
	}

	override fun createView() {
		Log.d(this, "createView")
	}

	override fun viewCreated() {
		Log.d(this, "viewCreated")
	}

	override fun started() {
		Log.d(this, "started")
	}

	override fun resumed() {
		Log.d(this, "resumed")
	}

	override fun paused() {
		Log.d(this, "paused")
		pauseDisposable.clear()
	}

	override fun stopped() {
		Log.d(this, "stopped")
		stopDisposable.clear()
	}

	override fun destroyView() {
		Log.d(this, "destroyView")
		destroyDisposable.clear()
	}

	override fun viewDestroyed() {
		Log.d(this, "viewDestroyed")
	}

	override fun detached() {
		Log.d(this, "detached")
		this.view = null
	}

	override fun onBackPressed(): Boolean {
		router?.exit()
		return true
	}
}