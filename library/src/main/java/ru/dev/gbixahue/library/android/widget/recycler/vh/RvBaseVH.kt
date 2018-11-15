package ru.dev.gbixahue.library.android.widget.recycler.vh

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class RvBaseVH<D>(itemView: View): RecyclerView.ViewHolder(itemView) {

	abstract fun bind(item: D)

	protected fun <V> bindView(id: Int): V = itemView.findViewById<View>(id) as V
}
