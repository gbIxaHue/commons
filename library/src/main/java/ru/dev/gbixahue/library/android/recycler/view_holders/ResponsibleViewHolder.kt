package ru.dev.gbixahue.library.android.recycler.view_holders

import android.view.View
import ru.dev.gbixahue.library.android.recycler.OnItemClickListener

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
open class ResponsibleViewHolder<D: Any>(itemView: View, protected val listener: OnItemClickListener<D>? = null):
    DataViewHolder<D>(itemView), View.OnClickListener {

  protected lateinit var itemData: D

  init {
    setOnClickListenerToView(itemView)
  }

  protected open fun setOnClickListenerToView(view: View) {
    view.setOnClickListener(this)
  }

  override fun bindData(data: D) {
    itemData = data
  }

  override fun onClick(v: View?) {
    listener?.onItemClick(itemData, itemViewType)
  }
}