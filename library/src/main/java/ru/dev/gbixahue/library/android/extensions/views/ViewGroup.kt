package ru.dev.gbixahue.library.android.extensions.views

import android.animation.LayoutTransition
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
@Suppress("UNCHECKED_CAST")
fun <T> ViewGroup.inflate(@LayoutRes resId: Int, attachToRoot: Boolean = false): T =
    LayoutInflater.from(context).inflate(resId, this, attachToRoot) as T

fun ViewGroup.enableLayoutAnimation() {
  layoutTransition = LayoutTransition()
  layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
}