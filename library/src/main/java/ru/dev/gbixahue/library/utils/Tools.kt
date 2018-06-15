package ru.dev.gbixahue.library.utils

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun restartApp() {
  android.os.Process.killProcess(android.os.Process.myPid())
  System.exit(10)
}