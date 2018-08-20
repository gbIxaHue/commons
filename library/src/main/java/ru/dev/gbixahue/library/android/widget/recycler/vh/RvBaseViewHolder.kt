package ru.dev.gbixahue.library.android.widget.recycler.vh

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class RvBaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {

	abstract fun bind(item: T)

	protected fun <T> bindView(id: Int): T {
		return itemView.findViewById<View>(id) as T
	}
}
