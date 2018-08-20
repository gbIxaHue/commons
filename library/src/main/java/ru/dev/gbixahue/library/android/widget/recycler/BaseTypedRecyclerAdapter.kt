package ru.dev.gbixahue.library.android.widget.recycler

import android.support.v7.widget.RecyclerView
import ru.dev.gbixahue.library.android.widget.recycler.vh.RvBaseViewHolder

abstract class BaseTypedRecyclerAdapter<T, VH: RvBaseViewHolder<TypedData<T>>>: RecyclerView.Adapter<VH>() {

	protected var itemList = mutableListOf<TypedData<T>>()

	fun setItems(elements: MutableList<TypedData<T>>) {
		this.itemList = elements
	}

	override fun onBindViewHolder(holder: VH, position: Int) {
		holder.bind(itemList[position])
	}

	override fun getItemViewType(position: Int): Int = itemList[position].layoutId
	override fun getItemCount(): Int = itemList.size
}