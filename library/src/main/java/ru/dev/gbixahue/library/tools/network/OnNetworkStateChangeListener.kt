package ru.dev.gbixahue.library.tools.network

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface OnNetworkStateChangeListener {
  fun onChange(isConnected: Boolean)
}
