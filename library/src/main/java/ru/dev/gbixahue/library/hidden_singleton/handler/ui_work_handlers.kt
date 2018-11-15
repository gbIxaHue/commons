package ru.dev.gbixahue.library.hidden_singleton.handler

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper

/**
 * Created by Anton Zhilenkov on 20.08.18.
 */
val uiHandler: Handler = Handler(Looper.getMainLooper())
val workHandler: Handler = Handler(HandlerThread("Application worker thread").apply { start() }.looper)

fun post(msTime: Long, func: () -> Unit) {
	Handler().postDelayed({ func() }, msTime)
}

fun postUI(msTime: Long = 0, func: () -> Unit) {
	if (msTime > 0) uiHandler.postDelayed({ func() }, msTime) else uiHandler.post(func)
}

fun postWork(msTime: Long = 0, func: () -> Unit) {
	if (msTime > 0) workHandler.postDelayed({ func() }, msTime) else workHandler.post(func)
}