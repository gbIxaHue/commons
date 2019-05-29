package ru.dev.gbixahue.library.extensions.views

import android.animation.ValueAnimator
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import android.view.View
import android.widget.Toast
import ru.dev.gbixahue.library.extensions.components.*
import ru.dev.gbixahue.library.hidden_singleton.handler.postUI
import ru.dev.gbixahue.library.utils.InnerArbEvaluator
import java.lang.ref.WeakReference

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun View.lock(byTime: Long) {
	isEnabled = false
	val wView = WeakReference<View>(this)
	postUI(byTime) { wView.get()?.isEnabled = true }
}

fun View.isRtl(): Boolean = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
fun View.weakReference() = WeakReference<View>(this)

fun View.colorFrom(@ColorRes colorId: Int) = context.colorFrom(colorId)
fun View.drawableFrom(@DrawableRes drawableId: Int) = context.drawableFrom(drawableId)
fun View.stringFrom(@StringRes stringId: Int) = context.stringFrom(stringId)
fun View.dimenFrom(@DimenRes dimenId: Int) = context.dimenFrom(dimenId)

fun View.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
	context.toast(message, length)
}

fun View.toast(messageId: Int, length: Int = Toast.LENGTH_SHORT) {
	context.toast(messageId, length)
}

fun View.dpToPx(dp: Float): Float = context.dpToPx(dp)
fun View.pxToDp(px: Float): Float = context.pxToDp(px)

fun View.enable(enable: Boolean) {
	isEnabled = enable
}

fun View.show(show: Boolean) {
	if (show && visibility == View.VISIBLE) return
	if (!show && visibility == View.GONE) return
	visibility = if (show) View.VISIBLE else View.GONE
}

fun View.changeBackgrounColor(colorId: Int, prevColorForAnimationId: Int = -1, duration: Long = 300) {
	val toColor = this.context.colorFrom(colorId)
	val fromColor = this.context.colorFrom(prevColorForAnimationId)
	if (fromColor == -1) {
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

