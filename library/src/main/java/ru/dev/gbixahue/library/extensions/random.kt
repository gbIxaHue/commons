package ru.dev.gbixahue.library.extensions

import java.util.*

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun randomRange(from: Int, to: Int): Int {
	return Random().nextInt(to - from) + from
}