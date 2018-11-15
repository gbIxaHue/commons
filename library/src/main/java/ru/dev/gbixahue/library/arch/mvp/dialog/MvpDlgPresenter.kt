package ru.dev.gbixahue.library.arch.mvp.dialog

/**
 * Created by Anton Zhilenkov on 21.08.18.
 */
interface MvpDlgPresenter<T: MvpDlgView> {

	fun attached(view: T)
	fun contentViewSet()

	fun created()
	fun started()
	fun showed()
	fun stopped()
	fun dismissed()
	fun cancelled()
	fun detached()
}