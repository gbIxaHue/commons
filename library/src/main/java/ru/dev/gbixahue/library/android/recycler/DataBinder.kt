package ru.dev.gbixahue.library.android.recycler

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface DataBinder<D> {
  fun bindData(data: D)
}