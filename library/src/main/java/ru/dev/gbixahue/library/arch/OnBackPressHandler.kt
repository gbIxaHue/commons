package ru.dev.gbixahue.library.arch

/**
 * Created by Anton Zhilenkov on 18.07.18.
 */

interface OnBackPressHandler {

	fun onBackPressed(): Boolean
}