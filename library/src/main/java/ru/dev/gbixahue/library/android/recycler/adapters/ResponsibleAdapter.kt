package ru.dev.gbixahue.library.android.recycler.adapters

import android.view.View
import android.view.ViewGroup
import ru.dev.gbixahue.library.android.LayoutHolder
import ru.dev.gbixahue.library.android.extensions.views.inflate
import ru.dev.gbixahue.library.android.recycler.OnItemClickListener
import ru.dev.gbixahue.library.android.recycler.view_holders.ResponsibleViewHolder

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
abstract class ResponsibleAdapter<V: ResponsibleViewHolder<D>, D: Any>(private val onItemClickListener: OnItemClickListener<D>):
    BaseDataBindAdapter<V, D>(), LayoutHolder {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
    val view = parent.inflate<View>(getLayoutId(), false)
    val holder = ResponsibleViewHolder<D>(view, onItemClickListener)
    return holder as V
  }
}