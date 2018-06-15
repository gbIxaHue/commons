package ru.dev.gbixahue.library.utils

import android.os.Handler
import android.os.Looper
import ru.dev.gbixahue.library.android.activity.LifeCycleListener
import ru.dev.gbixahue.library.android.log.Log
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
open class Repeatable(private val from: Long, private val to: Long = - 1): LifeCycleListener {

  var runAndSchedule = false

  private val handler: Handler = Handler(Looper.getMainLooper())
  private var repeatableAction: (() -> Unit)? = null
  private var runnable: Runnable? = null

  private val isRandom: Boolean by lazy { to > 0 && to > from }
  private val isScheduled: AtomicBoolean = AtomicBoolean(false)

  fun setRepeatableAction(repeatableAction: () -> Unit): Repeatable {
    this.repeatableAction = repeatableAction
    return this
  }

  override fun activityResumed() {
    if (repeatableAction == null) {
      Log.e(this, "RepeatableAction doesn't set. Repeating is shutting down")
      resetSchedule()
      return
    }
    if (runAndSchedule) repeatableAction?.invoke()

    if (isScheduled.getAndSet(true)) return
    val delayedValue = if (isRandom) getRandom(from.toInt(), to.toInt()).toLong() else from
    runnable = object: Runnable {
      override fun run() {
        repeatableAction?.invoke()
        handler.postDelayed(this, delayedValue)
      }
    }
    handler.postDelayed(runnable, delayedValue)
  }

  override fun activityOnStop() {
    resetSchedule()
  }

  override fun activityOnDestroy() {
    resetSchedule()
    repeatableAction = null
  }

  private fun resetSchedule() {
    handler.removeCallbacks(runnable)
    isScheduled.set(false)
  }
}