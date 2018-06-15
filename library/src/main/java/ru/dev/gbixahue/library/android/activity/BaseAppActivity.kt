package ru.dev.gbixahue.library.android.activity

import ru.dev.gbixahue.library.android.BaseApplication
import ru.dev.gbixahue.library.android.preference.PreferenceRepository

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
open class BaseAppActivity: BaseActivity(), LifeCycleActivity {

  protected val prefRepo: PreferenceRepository = (applicationContext as BaseApplication).prefRepo

  private val lifecycleListeners: MutableList<LifeCycleListener> = mutableListOf()

  override fun addLifeCycleListener(listener: LifeCycleListener) {
    lifecycleListeners.add(listener)
  }

  override fun removeLifeCycleListener(listener: LifeCycleListener) {
    lifecycleListeners.remove(listener)
  }

  override fun onStart() {
    super.onStart()
    lifecycleListeners.forEach { it.activityStarted() }
  }

  override fun onResume() {
    super.onResume()
    lifecycleListeners.forEach { it.activityResumed() }
  }

  override fun onPause() {
    lifecycleListeners.forEach { it.activityOnPause() }
    super.onPause()
  }

  override fun onStop() {
    lifecycleListeners.forEach { it.activityOnStop() }
    super.onStop()
  }

  override fun onDestroy() {
    lifecycleListeners.forEach { it.activityOnDestroy() }
    lifecycleListeners.clear()
    super.onDestroy()
  }
}