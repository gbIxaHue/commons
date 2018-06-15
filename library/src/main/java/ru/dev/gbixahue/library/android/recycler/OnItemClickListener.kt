package ru.dev.gbixahue.library.android.recycler

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
interface OnItemClickListener<D> {
  fun onItemClick(data: D, viewType: Int = - 1)
}