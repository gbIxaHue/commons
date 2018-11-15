package ru.dev.gbixahue.library.hidden_singleton

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import ru.dev.gbixahue.library.BuildConfig
import ru.dev.gbixahue.library.extensions.components.toast
import ru.dev.gbixahue.library.extensions.views.toast

/**
 * Created by Anton Zhilenkov on 16.10.18.
 */
object AppInfoHelper {

	private val AWAIT = 500L
	private val CLICKS = 3

	private var countOfClicks = 0
	private var timeOfLastClick = 0L

	fun getInfo(context: Context? = null): String {
		return if (context == null) getSimpleInfo()
		else getExtendedInfo(context)
	}

	fun showInfo(view: View) {
		if (view is TextView) view.text = getInfo(view.context)
		else showInfo(view.context)
	}

	fun showInfo(context: Context) {
		context.toast(getInfo(context), Toast.LENGTH_LONG)
	}

	fun showInfoByClick(view: View, await: Long = AWAIT, maxClick: Int = CLICKS, infoInterceptor: ((String) -> Unit)? = null) {
		view.setOnClickListener {
			if (!isTimeToShow(await, maxClick)) return@setOnClickListener

			reset()
			val info = getInfo(view.context)

			if (infoInterceptor == null) view.toast(info, Toast.LENGTH_LONG)
			else infoInterceptor.invoke(info)
		}
	}

	private fun isTimeToShow(await: Long, maxClick: Int): Boolean {
		val curTime = System.currentTimeMillis()
		if (timeOfLastClick == 0L) timeOfLastClick = curTime
		if (curTime - timeOfLastClick >= await) {
			reset()
		} else {
			timeOfLastClick = curTime
			countOfClicks++
		}
		return countOfClicks == maxClick
	}

	private fun reset() {
		timeOfLastClick = 0
		countOfClicks = 0
	}

	private fun getSimpleInfo(): String {
		return BuildConfig.VERSION_NAME
	}

	private fun getExtendedInfo(context: Context): String {
		return BuildConfig.VERSION_NAME
	}
}