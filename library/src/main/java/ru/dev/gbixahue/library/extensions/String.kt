package ru.dev.gbixahue.library.extensions

/**
 * Created by Anton Zhilenkov on 12.12.17.
 */
fun String.coloriseByHtml(hexColor: String): String = "<font color=$hexColor>$this</font>"