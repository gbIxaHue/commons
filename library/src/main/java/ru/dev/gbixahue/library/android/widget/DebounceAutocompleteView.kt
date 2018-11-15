package ru.dev.gbixahue.library.android.widget

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.util.AttributeSet

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class DebounceAutocompleteView: AppCompatAutoCompleteTextView {

	constructor (context: Context): super(context)
	constructor (context: Context, attrs: AttributeSet): super(context, attrs)
	constructor (context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

	private val debounceHandler: Handler = Handler(Looper.getMainLooper())

	private val runnable = object: DataRunnable(this) {
		override fun runWithData(value: CharSequence?) {
			value?.let { autocomlete.superPerformFiltering(it, 0) }
		}
	}

	private var timeOut = 750L
	private var minLength = 2

	override fun performFiltering(text: CharSequence, keyCode: Int) {
		debounceHandler.removeCallbacksAndMessages(runnable)
		if (text.length < minLength) return

		runnable.updateValue(text)
		debounceHandler.postDelayed(runnable, timeOut)
	}

	fun changeTimeOut(timeOut: Long) {
		this.timeOut = timeOut
	}

	fun setMinLength(length: Int) {
		minLength = length
	}

	private fun superPerformFiltering(text: CharSequence, keyCode: Int){
		super.performFiltering(text, keyCode)
	}

	internal abstract class DataRunnable(protected val autocomlete: DebounceAutocompleteView): Runnable {

		private var value: CharSequence? = null

		fun updateValue(newValue: CharSequence) {
			value = newValue
		}

		override fun run() {
			runWithData(value)
		}

		abstract fun runWithData(value: CharSequence?)
	}
}
