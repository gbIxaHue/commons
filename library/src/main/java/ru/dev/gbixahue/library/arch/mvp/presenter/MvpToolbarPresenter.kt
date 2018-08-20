package ru.dev.gbixahue.library.arch.mvp.presenter

import ru.dev.gbixahue.library.arch.components.toolbar.ToolbarBuilder
import ru.dev.gbixahue.library.arch.components.toolbar.ToolbarHolder
import ru.dev.gbixahue.library.arch.components.toolbar.ToolbarManager
import ru.dev.gbixahue.library.arch.mvp.view.MvpView

/**
 * Created by Anton Zhilenkov on 17.07.18.
 */
abstract class MvpToolbarPresenter<View: MvpView>: BaseMvpPresenter<View>() {

	/**
	 * ToolbarManager is created in the Fragment.onCreateView() method by calling presenter.createView().
	 * An instance of ToolbarManager persist in the presenter and in the Fragment view
	 */
	var toolbarManager: ToolbarManager? = null
		private set

	override fun createView() {
		super.createView()
		(view as? ToolbarHolder)?.let { holder ->
			toolbarManager = holder.createToolbarManager(getToolbarBuilder())
		}
	}

	protected abstract fun getToolbarBuilder(): ToolbarBuilder?
}