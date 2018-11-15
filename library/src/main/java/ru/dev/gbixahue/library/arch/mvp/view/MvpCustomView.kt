package ru.dev.gbixahue.library.arch.mvp.view

/**
 * Created by Anton Zhilenkov on 16.07.18.
 */
interface MvpCustomView: MvpView {

	fun onViewCreated()
	fun onStart()
	fun onResume()
	fun onPause()
	fun onStop()
	fun onDestroyView()
	fun onViewDestroyed()
	fun onDetach()
}