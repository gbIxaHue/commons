package ru.dev.gbixahue.library.arch.mvp.presenter

import ru.dev.gbixahue.library.arch.mvp.view.MvpView

interface MvpPresenter<T: MvpView> {

	fun attached(view: T)
	fun created()
	fun createView()
	fun viewCreated()
	fun started()
	fun resumed()
	fun paused()
	fun stopped()
	fun destroyView()
	fun viewDestroyed()
	fun detached()
}