package ru.dev.gbixahue.library.extensions.components

import java.lang.ref.Reference

/**
 * Created by Anton Zhilenkov on 24.10.18.
 */

inline fun <T, R> Reference<T>.perform(action: (T) -> R) {
	get()?.let { action(it) }
}