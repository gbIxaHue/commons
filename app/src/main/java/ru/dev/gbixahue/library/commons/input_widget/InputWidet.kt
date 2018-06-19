package ru.dev.gbixahue.library.commons.input_widget

import android.content.Context
import android.content.res.TypedArray
import android.support.transition.TransitionManager
import android.support.v7.widget.AppCompatEditText
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.commons.R
import ru.dev.gbixahue.library.extensions.components.convertDpToPx
import ru.dev.gbixahue.library.extensions.components.stringFrom
import ru.dev.gbixahue.library.extensions.views.convertPxToDp
import ru.dev.gbixahue.magicoflove.ShowAnimator


/**
 * Created by Anton Zhilenkov on 15.06.18.
 */
class InputWidget: LinearLayout {
// https://gist.github.com/laaptu/ee2d30c46e14743172cb
// http://code.i-harness.com/en/q/d1563f - better

	private val mCurrentApiVersion = android.os.Build.VERSION.SDK_INT

	var isErrorEnabled = false
		set(value) {
			field = value
			TransitionManager.beginDelayedTransition(parent as ViewGroup)
			mLabelView.visibility = if (field) View.VISIBLE else View.GONE
		}

	private lateinit var mEditText: AppCompatEditText
	private var mHint: String? = null

	private lateinit var mHintView: TextView
	private lateinit var mHintAnimator: ShowAnimator
	private lateinit var mLabelView: TextView

	private var mHighLightColor: Int = android.R.color.primary_text_light
	private var mErrorColor: Int = android.R.color.primary_text_light

	private val list = mutableListOf<OnFocusChangeListener>()


	val text: String
		get() = mEditText.text.toString()

	constructor(context: Context): super(context) {
		inflateViews()
	}

	constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
		inflateViews()
		readAttributes(attrs)
	}

	constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {
		inflateViews()
		readAttributes(attrs)
	}

	private fun inflateViews() {
		orientation = LinearLayout.VERTICAL
		val view = LayoutInflater.from(context).inflate(R.layout.w_input, this, true)

		mEditText = view.findViewById(R.id.input)
		mHintView = view.findViewById(R.id.hint)
		mLabelView = view.findViewById(R.id.label)

		mHintAnimator = ShowAnimator(mHintView, R.anim.edittext_label_slide_from_bottom, R.anim.edittext_label_slide_to_bottom)
	}

	private fun readAttributes(attrs: AttributeSet) {
		val widgetAttributeSet = context.obtainStyledAttributes(attrs, R.styleable.InputWidget) ?: return
		findStyle(widgetAttributeSet)?.let {
			setupEditText(it, getString(context, widgetAttributeSet, R.styleable.InputWidget_inputText))
			setupHintView(it, getString(context, widgetAttributeSet, R.styleable.InputWidget_hintText))
			setupLabelView(it, getString(context, widgetAttributeSet, R.styleable.InputWidget_labelText))
			it.recycle()
		}
		widgetAttributeSet.recycle()
	}

	private fun setupEditText(styleAttributes: TypedArray, text: String?) {
		Log.d(this, styleAttributes.indexCount)
		text?.let { mEditText.setText(it) }
		mEditText.maxLines = 1
		mEditText.ellipsize = TextUtils.TruncateAt.END
		mEditText.requestLayout()

		val textColor = styleAttributes.getColor(R.styleable.InputWidget_inputTextColor, NOT_FOUND)
		if (textColor != NOT_FOUND) mHintView.setTextColor(textColor)
		val textSize = styleAttributes.getDimensionPixelSize(R.styleable.InputWidget_inputTextSize, NOT_FOUND).toFloat()
		if (textColor != NOT_FOUND) mHintView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mHintView.convertPxToDp(textSize))

		val padding = dpToPx(24)
		mEditText.setPadding(padding, 0, padding, padding)

		mEditText.setOnFocusChangeListener { view, hasFocus ->
			mHintAnimator.updateColor(hasFocus, isErrorEnabled)
			if (hasFocus) {
				mEditText.hint = ""
				mHintAnimator.show(true)
			} else {
				if (mEditText.text.isEmpty()) {
					mHintAnimator.show(false, { mEditText.hint = mHint })
				}
			}
			list.forEach { it.onFocusChange(view, hasFocus) }
		}
	}

	private fun setupHintView(styleAttributes: TypedArray, text: String?) {
		val textColor = styleAttributes.getColor(R.styleable.InputWidget_hintTextColor, NOT_FOUND)
		if (textColor != NOT_FOUND) mHintView.setTextColor(textColor)
		val textSize = styleAttributes.getDimensionPixelSize(R.styleable.InputWidget_hintTextSize, NOT_FOUND).toFloat()
		if (textColor != NOT_FOUND) mHintView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mHintView.convertPxToDp(textSize))

		mHintAnimator.focusedColor = textColor
		val padding = mEditText.paddingLeft / 2
		mHintView.setPadding(mEditText.paddingLeft, padding, mEditText.paddingRight, 0)
		text?.let {
			mHint = it
			mHintView.text = it
			mEditText.hint = it
			if (mEditText.text.isNotEmpty()) mHintAnimator.show(true)
		}
	}

	private fun setupLabelView(styleAttributes: TypedArray, text: String?) {
		text?.let { mLabelView.text = it }

		val textColor = styleAttributes.getColor(R.styleable.InputWidget_labelTextColor, NOT_FOUND)
		if (textColor != NOT_FOUND) mLabelView.setTextColor(textColor)
		val textSize = styleAttributes.getDimensionPixelSize(R.styleable.InputWidget_labelTextSize, NOT_FOUND).toFloat()
		if (textColor != NOT_FOUND) mLabelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLabelView.convertPxToDp(textSize))
	}

	private fun dpToPx(dp: Int): Int = context.convertDpToPx(dp.toFloat()).toInt()

	private fun findStyle(typedArray: TypedArray): TypedArray? {
		val styleId = typedArray.getResourceId(R.styleable.InputWidget_iwStyle, - 1)
		if (styleId <= 0) return null
		return context.obtainStyledAttributes(styleId, R.styleable.InputWidget)
	}

	override fun willNotDraw(): Boolean = true

	override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
		if (l == null) {
			list.clear()
		} else list.add(l)
	}

	fun addTextChangedListener(watcher: TextWatcher) {
		mEditText.addTextChangedListener(watcher)
	}

	fun removeTextChangedListener(watcher: TextWatcher) {
		mEditText.removeTextChangedListener(watcher)
	}

	companion object {
		const val NOT_FOUND = - 1

		fun getString(context: Context, typedArray: TypedArray, styleable: Int): String? {
			val resId = typedArray.getResourceId(styleable, NOT_FOUND)
			return if (resId == NOT_FOUND) null else context.stringFrom(resId)
		}
	}
}