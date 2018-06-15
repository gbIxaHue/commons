package ru.dev.gbixahue.library.android.activity

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface LifeCycleListener {
  fun activityOnPause() {}
  fun activityResumed() {}

  fun activityStarted() {}
  fun activityOnStop() {}
  fun activityOnDestroy() {}
}