package ru.dev.gbixahue.library.android.widget.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import ru.dev.gbixahue.library.android.LayoutHolder

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
abstract class BaseListAdapter<V: View, D>(protected val itemList: MutableList<D> = mutableListOf()): BaseAdapter(), LayoutHolder {

  fun setItemList(newItemList: MutableList<D>) {
    itemList.clear()
    newItemList.forEach { itemList.add(it) }
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    var view = convertView
    if (view == null) {
      view = LayoutInflater.from(parent.context).inflate(getLayoutId(), parent, false)
    }
    bindView((view as V?) !!, itemList[position])
    return view !!
  }

  abstract fun bindView(view: V, item: D)

  override fun getItem(position: Int): D = itemList[position]

  override fun getItemId(position: Int): Long = position.toLong()

  override fun getCount(): Int = itemList.size
}