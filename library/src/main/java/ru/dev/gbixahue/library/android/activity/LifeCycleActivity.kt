package ru.dev.gbixahue.library.android.activity

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface LifeCycleActivity {
  fun addLifeCycleListener(listener: LifeCycleListener)
  fun removeLifeCycleListener(listener: LifeCycleListener)
}