package ru.dev.gbixahue.magicoflove

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import ru.dev.gbixahue.library.commons.input_widget.FloatingAnimator
import ru.dev.gbixahue.library.extensions.views.colorFrom

/**
 * Created by Anton Zhilenkov on 15.06.18.
 */
internal class ShowAnimator(
		private val view: TextView,
		private val showAnimation: Int,
		private val hideAnimation: Int,
		private val hideMethod: Int = View.INVISIBLE
): FloatingAnimator {

	var focusedColor: Int = android.R.color.white
	var unFocusedColor: Int = android.R.color.black

	private var isShowing = false

	private val toUnfocusAnimation: ValueAnimator by lazy { getFocusAnimation(unFocusedColor, focusedColor) }
	private val toFocusAnimation: ValueAnimator by lazy { getFocusAnimation(focusedColor, unFocusedColor) }

	override fun updateColor(inFocus: Boolean, isError: Boolean) {
		val animation = if (inFocus) toFocusAnimation else toUnfocusAnimation
		animation.duration = 700
		animation.start()
	}

	override fun show(show: Boolean, callback: (() -> Unit)?) {
		if (isShowing && show) return
		if (show) show(callback) else hide(callback)
		isShowing = show
	}

	private fun show(callback: (() -> Unit)?) {
		view.visibility = View.VISIBLE
		val animation = AnimationUtils.loadAnimation(view.context, showAnimation)
		animation.onAnimationEnd(callback)
		view.startAnimation(animation)
	}

	private fun hide(callback: (() -> Unit)?) {
		view.visibility = hideMethod
		val animation = AnimationUtils.loadAnimation(view.context, hideAnimation)
		animation.onAnimationEnd(callback)
		view.startAnimation(animation)
	}

	private fun getFocusAnimation(fromColor: Int, toColor: Int): ValueAnimator {
		val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
		colorAnimation.addUpdateListener { animator -> view.setTextColor(view.colorFrom(animator.animatedValue as Int)) }
		return colorAnimation
	}

	companion object {
		fun Animation.onAnimationEnd(callback: (() -> Unit)?) {
			setAnimationListener(object: Animation.AnimationListener {
				override fun onAnimationStart(animation: Animation?) {}
				override fun onAnimationRepeat(animation: Animation?) {}
				override fun onAnimationEnd(animation: Animation?) {
					callback?.invoke()
				}
			})
		}
	}
}