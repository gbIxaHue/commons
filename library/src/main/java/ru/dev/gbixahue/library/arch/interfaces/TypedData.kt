package ru.dev.gbixahue.library.arch.interfaces

/**
 * Created by Anton Zhilenkov on 16.06.17.
 */

interface TypedData<D>: Typed<D> {

	val data: Any?
}
