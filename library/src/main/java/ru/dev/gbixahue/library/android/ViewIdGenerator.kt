package ru.dev.gbixahue.library.android

import android.os.Build
import android.view.View
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class ViewIdGenerator {
  private val sNextGeneratedId = AtomicInteger(1)

  fun generateViewId(): Int {
    if (Build.VERSION.SDK_INT < 17) {
      while (true) {
        val result = sNextGeneratedId.get()
        var newValue = result + 1
        if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
        if (sNextGeneratedId.compareAndSet(result, newValue)) return result
      }
    } else {
      return View.generateViewId()
    }
  }
}