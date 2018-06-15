package ru.dev.gbixahue.library.android.recycler.adapters

import ru.dev.gbixahue.library.android.recycler.view_holders.DataViewHolder

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
abstract class BaseDataBindAdapter<V: DataViewHolder<D>, D>: BaseAdapter<V, D>() {

  override fun onBindViewHolder(holder: V, position: Int) {
    holder.bindData(itemList[position])
  }
}