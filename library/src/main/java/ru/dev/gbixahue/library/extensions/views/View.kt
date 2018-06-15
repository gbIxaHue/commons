package ru.dev.gbixahue.library.extensions.views

import android.animation.ValueAnimator
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.View
import android.widget.ImageView
import ru.dev.gbixahue.library.extensions.components.*
import ru.dev.gbixahue.library.utils.InnerArbEvaluator
import java.lang.ref.WeakReference

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun View.weakReference() = WeakReference<View>(this)

fun View.colorFrom(@ColorRes colorId: Int) = context.colorFrom(colorId)
fun View.drawableFrom(@DrawableRes drawableId: Int) = context.drawableFrom(drawableId)
fun View.stringFrom(@StringRes stringId: Int) = context.stringFrom(stringId)
fun View.dimenFrom(@DimenRes dimenId: Int) = context.dimenFrom(dimenId)

fun View.convertDpToPx(dp: Float): Float = context.convertDpToPx(dp)
fun View.convertPxToDp(px: Float): Float = context.convertPxToDp(px)

fun View.enable(enable: Boolean) {
	isEnabled = enable
}

fun View.show(show: Boolean) {
	if (show && visibility == View.VISIBLE) return
	if (! show && visibility == View.GONE) return
	visibility = if (show) View.VISIBLE else View.GONE
}

fun View.changeBackgrounColor(colorId: Int, prevColorForAnimationId: Int = - 1, duration: Long = 300) {
	val toColor = this.context.colorFrom(colorId)
	val fromColor = this.context.colorFrom(prevColorForAnimationId)
	if (fromColor == - 1) {
		setBackgroundColor(toColor)
	} else {
		val animator = ValueAnimator()
		animator.setIntValues(fromColor, toColor)
		animator.setEvaluator(InnerArbEvaluator())
		animator.duration = duration
		animator.addUpdateListener { animation ->
			setBackgroundColor(animation.animatedValue as Int)
		}
		animator.start()
	}
}

fun ImageView.show(show: Boolean) {
	if (show && visibility == View.VISIBLE) return
	if (! show && visibility == View.INVISIBLE) return
	visibility = if (show) View.VISIBLE else View.INVISIBLE
}
