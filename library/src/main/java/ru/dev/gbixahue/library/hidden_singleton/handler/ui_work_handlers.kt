package ru.dev.gbixahue.library.hidden_singleton.handler

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper

/**
 * Created by Anton Zhilenkov on 20.08.18.
 */
val uiHandler: Handler = Handler(Looper.getMainLooper())
val workHandler: Handler = Handler(HandlerThread("Application worker thread").apply { start() }.looper)

fun postUi(func: () -> Unit, delay: Long = 0L) {
	uiHandler.postDelayed(func, delay)
}

fun postWork(func: () -> Unit, delay: Long = 0L) {
	workHandler.postDelayed(func, delay)
}