package ru.dev.gbixahue.library.android

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface OnBackPressHandler {

  /**
   * @return true it an item handle back press
   */
  fun onBackPressed(): Boolean
}