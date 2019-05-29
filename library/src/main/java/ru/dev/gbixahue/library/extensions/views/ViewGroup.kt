package ru.dev.gbixahue.library.extensions.views

import android.animation.LayoutTransition
import androidx.annotation.*
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import ru.dev.gbixahue.library.extensions.components.*
import java.lang.ref.WeakReference

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun ViewGroup.weakReference() = WeakReference<ViewGroup>(this)

fun ViewGroup.colorFrom(@ColorRes colorId: Int) = context.colorFrom(colorId)
fun ViewGroup.drawableFrom(@DrawableRes drawableId: Int) = context.drawableFrom(drawableId)
fun ViewGroup.stringFrom(@StringRes stringId: Int) = context.stringFrom(stringId)
fun ViewGroup.dimenFrom(@DimenRes dimenId: Int) = context.dimenFrom(dimenId)

fun ViewGroup.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
	context.toast(message, length)
}

fun ViewGroup.toast(messageId: Int, length: Int = Toast.LENGTH_SHORT) {
	context.toast(messageId, length)
}

@Suppress("UNCHECKED_CAST")
fun <T> ViewGroup.inflate(@LayoutRes resId: Int, attachToRoot: Boolean = false): T =
		LayoutInflater.from(context).inflate(resId, this, attachToRoot) as T

fun ViewGroup.enableLayoutAnimation() {
	layoutTransition = LayoutTransition()
	layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
}