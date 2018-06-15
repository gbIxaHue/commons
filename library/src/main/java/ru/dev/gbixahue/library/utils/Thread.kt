package ru.dev.gbixahue.library.utils

import android.os.Handler

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun postDelayed(func: () -> Unit, msTime: Long) {
  Handler().postDelayed({ func() }, msTime)
}