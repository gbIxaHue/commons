package ru.dev.gbixahue.library.commons.input_widget

import android.animation.ValueAnimator
import android.view.View
import android.widget.EditText

/**
 * Created by Anton Zhilenkov on 15.06.18.
 */
class FocusListener(
		private val parent: EditText,
		private val unFocusAnimation: ValueAnimator,
		private val focusAnimtion: ValueAnimator
): View.OnFocusChangeListener {

	var editable: Boolean = false
	var hasError: Boolean = false

	var changeColorCallback: ((Int) -> Unit)? = null

	override fun onFocusChange(v: View, hasFocus: Boolean) {
		val lColorAnimation: ValueAnimator?
		if (! editable) return
		val drawable = parent.background
		if (hasError) {

//			changeColorCallback?.invoke(R.color.sdk_text_payment_error)
//			drawable.setColorFilter(parent.colorFrom(R.color.sdk_text_payment_error), PorterDuff.Mode.SRC_ATOP)
		} else {
			if (hasFocus) {
				lColorAnimation = unFocusAnimation
//				drawable.setColorFilter(parent.colorFrom(R.color.sdk_primary_new), PorterDuff.Mode.SRC_ATOP)
			} else {
				lColorAnimation = focusAnimtion
//				drawable.setColorFilter(parent.colorFrom(R.color.sdk_accent_new_70), PorterDuff.Mode.SRC_ATOP)
			}

			lColorAnimation.duration = 700
			lColorAnimation.start()
		}
		parent.background = drawable
	}
}