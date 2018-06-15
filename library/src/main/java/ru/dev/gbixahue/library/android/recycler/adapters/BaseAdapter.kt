package ru.dev.gbixahue.library.android.recycler.adapters

import android.support.v7.widget.RecyclerView

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
abstract class BaseAdapter<V: RecyclerView.ViewHolder, D>: RecyclerView.Adapter<V>() {

  protected val itemList: MutableList<D> = mutableListOf()

  fun setItemList(newItemList: MutableList<D>) {
    itemList.clear()
    newItemList.forEach { itemList.add(it) }
  }

  override fun getItemCount(): Int = itemList.size
}