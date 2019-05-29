package ru.dev.gbixahue.library.android.widget.recycler.vh

import androidx.recyclerview.widget.RecyclerView
import android.view.View

abstract class RvBaseVH<D>(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

	abstract fun bind(item: D)

	protected fun <V> bindView(id: Int): V = itemView.findViewById<View>(id) as V
}
