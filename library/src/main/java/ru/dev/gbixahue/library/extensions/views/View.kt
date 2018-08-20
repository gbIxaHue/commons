package ru.dev.gbixahue.library.extensions.views

import android.animation.ValueAnimator
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Point
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
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

fun View.dpToPx(dp: Float): Float = context.dpToPx(dp)
fun View.pxToDp(px: Float): Float = context.pxToDp(px)

fun View.enable(enable: Boolean) {
	isEnabled = enable
}

fun View.changeBackgrounColor(@ColorRes colorId: Int, @ColorRes prevColorForAnimationId: Int = - 1, duration: Long = 300) {
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

fun View.show(show: Boolean) {
	if (show && visibility == View.VISIBLE) return
	if (! show && visibility == View.GONE) return
	visibility = if (show) View.VISIBLE else View.GONE
}

fun ImageView.show(show: Boolean) {
	if (show && visibility == View.VISIBLE) return
	if (! show && visibility == View.INVISIBLE) return
	visibility = if (show) View.VISIBLE else View.INVISIBLE
}

fun Context.displayDensity(): Float {
	val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
	val metrics = DisplayMetrics()
	display.getMetrics(metrics)
	return metrics.density
}

fun Context.displayWidthInPx(): Float {
	val service = (getSystemService(Context.WINDOW_SERVICE) as? WindowManager) ?: return 0f
	val point = Point()
	service.defaultDisplay.getSize(point)
	return point.x.toFloat()
}

fun Context.displayHeightInPx(): Float {
	val service = (getSystemService(Context.WINDOW_SERVICE) as? WindowManager) ?: return 0f
	val point = Point()
	service.defaultDisplay.getSize(point)
	return point.y.toFloat()
}

fun Context.openWebView(url: String) {
	try {
		val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
		applicationContext.startActivity(myIntent)
	} catch (e: ActivityNotFoundException) {
		e.printStackTrace()
		showToast(e.localizedMessage)
	}
}

fun Context.isPortrait(): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
fun Context.getOrientation(): Int = resources.configuration.orientation
fun isPortrait(orientation: Int): Boolean = orientation == Configuration.ORIENTATION_PORTRAIT
