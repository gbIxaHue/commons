package ru.dev.gbixahue.library.utils

import java.util.*

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun getRandom(from: Int, to: Int): Int {
  return Random().nextInt(to - from) + from
}