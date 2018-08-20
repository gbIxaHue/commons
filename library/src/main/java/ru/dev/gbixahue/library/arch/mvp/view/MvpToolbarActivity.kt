package ru.dev.gbixahue.library.arch.mvp.view

import ru.dev.gbixahue.library.arch.components.toolbar.ToolbarBuilder
import ru.dev.gbixahue.library.arch.components.toolbar.ToolbarHolder
import ru.dev.gbixahue.library.arch.components.toolbar.ToolbarManager

/**
 * Created by Anton Zhilenkov on 17.07.18.
 */
abstract class MvpToolbarActivity: BaseMvpActivity(), ToolbarHolder {

	/**
	 * ToolbarManager is created in the Activity.onCreate(), after setContentView() method by calling Presenter.createView().
	 * An instance of ToolbarManager persist in the Presenter and in the Activity
	 */
	protected var toolbarManager: ToolbarManager? = null

	override fun createToolbarManager(builder: ToolbarBuilder?): ToolbarManager? {
		builder?.let { toolbarManager = ToolbarBuilder.prepareToolbar(it, findViewById(android.R.id.content)) }
		return toolbarManager
	}
}