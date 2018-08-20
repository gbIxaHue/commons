package ru.dev.gbixahue.library.android.widget.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.dev.gbixahue.library.android.widget.recycler.vh.RvBaseViewHolder

abstract class RvBaseAdapter<D, VH: RvBaseViewHolder<D>>: RecyclerView.Adapter<VH>() {

	protected var itemList: MutableList<D> = mutableListOf()

	protected abstract val itemLayoutId: Int

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
		val itemView = LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
		return getViewHolder(itemView)
	}

	override fun onBindViewHolder(holder: VH, position: Int) {
		holder.bind(itemList[position])
	}

	override fun getItemCount(): Int = itemList.size

	open fun setItems(elements: MutableList<D>) {
		itemList.clear()
		itemList.addAll(elements)
	}

	open fun addItem(item: D) {
		itemList.add(item)
		notifyItemInserted(itemCount)
	}

	open fun removeItem(item: D) {
		val index = itemList.indexOf(item)
		if (index != - 1) {
			itemList.removeAt(index)
			notifyItemRemoved(index)
		}
	}

	protected abstract fun getViewHolder(itemView: View): VH
}
