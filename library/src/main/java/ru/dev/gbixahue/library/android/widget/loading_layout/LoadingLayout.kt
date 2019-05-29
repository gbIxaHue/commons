package ru.dev.gbixahue.library.android.widget.loading_layout

import android.content.Context
import android.content.res.TypedArray
import androidx.transition.TransitionManager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import ru.dev.gbixahue.library.R
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.extensions.views.inflate
import ru.dev.gbixahue.library.extensions.views.lock
import ru.dev.gbixahue.library.extensions.views.show

/**
 * Created by Anton Zhilenkov on 08.01.18.
 *
 * Требует доработки
 */
class LoadingLayout: FrameLayout {
	enum class State { NONE, LOADING, DONE, ERROR }

	companion object {
		const val ANIMATION_DURATION = 300L
	}

	var enableAnimation = true

	private lateinit var initialState: State
	private var prevState: State? = null

	var state: State = State.NONE
		set(value) {
			if (value != State.NONE && field == value) return
			field = value
			stateChanged(value)
		}

	private var errorView: View? = null
	private var mainContent: View? = null
	private lateinit var progressBar: ProgressBar

	private var repeatButton: View? = null

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
		readAttrs(context, attrs)
	}

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
		readAttrs(context, attrs)
	}

	private fun readAttrs(context: Context, attrs: AttributeSet) {
		val styleAttrs = context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0)
		try {
			associateErrorView(styleAttrs)
			initialState = when (styleAttrs.getInt(R.styleable.LoadingLayout_defState, -1)) {
				0 -> State.DONE
				1 -> State.LOADING
				2 -> State.ERROR
				3 -> State.NONE
				else -> State.NONE
			}
			prevState = initialState
		} finally {
			styleAttrs.recycle()
		}
		createProgressBar()
	}

	override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
		associateMainContent(child)
		if (mainContent != null) {
			state = initialState
			errorView?.let { addView(it) }
			super.addView(child, params)
			addView(progressBar)
		} else {
			super.addView(child, params)
		}
	}

	private fun associateMainContent(view: View?) {
		if (view == null) return

		//заменить ID'шником из xml
//		if (view.id != R.id.contentLayout) return
//		mainContent = view.apply { tag = "mainView" }
	}

	private fun associateErrorView(styleAttrs: TypedArray) {
		val layoutId = styleAttrs.getResourceId(R.styleable.LoadingLayout_errorLayoutId, -1)
		val repeatBtnId = styleAttrs.getResourceId(R.styleable.LoadingLayout_errorRepeatBtnId, -1)
		if (layoutId < 0) return
		val errorLayout: ViewGroup = this.inflate(layoutId, false)
		errorView = errorLayout.apply { tag = "errorView" }
		if (repeatBtnId > 0) repeatButton = errorLayout.findViewById(repeatBtnId)
	}

	fun setRepeatButtonClickListener(callback: (() -> Unit)?) {
		if (callback == null) {
			repeatButton?.show(false)
		} else {
			repeatButton?.show(true)
			repeatButton?.setOnClickListener {
				repeatButton?.lock(700)
				callback()
			}
		}
	}

	private fun createProgressBar() {
		progressBar = ProgressBar(context).apply {
			tag = "progressView"
			layoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
				gravity = Gravity.CENTER_HORIZONTAL
			}
		}
	}

	private fun stateChanged(state: State) {
		Log.d(this, "state: ${state.name}")
		when (state) {
			State.LOADING -> {
				updateVisibility(progressBar, true)
				updateVisibility(mainContent, false)
				updateVisibility(errorView, false, true)
			}
			State.DONE -> {
				if (enableAnimation && prevState == State.LOADING) TransitionManager.beginDelayedTransition(this)
				updateVisibility(errorView, false)
				updateVisibility(mainContent, true)
				if (enableAnimation) {
					postDelayed({ updateVisibility(progressBar, false, true) }, ANIMATION_DURATION)
				} else {
					updateVisibility(progressBar, false, true)
				}
			}
			State.ERROR -> {
				updateVisibility(errorView, true)
				updateVisibility(mainContent, false)
				updateVisibility(progressBar, false, true)
			}
			State.NONE -> {
				updateVisibility(mainContent, false)
				updateVisibility(progressBar, false)
				updateVisibility(errorView, false, true)
			}
		}
		prevState = state
	}

	private fun updateVisibility(view: View?, show: Boolean, log: Boolean = false) {
		if (view == null || view.tag == null) return

		view.show(show)
		if (log) {
			val state = if (show) "VISIBLE" else "GONE"
			Log.d(this, "${view.tag} change visibility to: $state")
		}
	}
}