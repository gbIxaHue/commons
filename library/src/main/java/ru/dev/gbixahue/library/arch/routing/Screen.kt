package ru.dev.gbixahue.library.arch.routing

import java.io.Serializable

/**
 * Created by Anton Zhilenkov on 15.01.18.
 */
interface Screen: Serializable {
	val name: String
}