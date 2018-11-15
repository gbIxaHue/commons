package ru.dev.gbixahue.library.android.widget.recycler

import android.support.v7.widget.RecyclerView
import ru.dev.gbixahue.library.android.widget.recycler.vh.RvBaseTypedVH
import ru.dev.gbixahue.library.arch.interfaces.TypedData

abstract class RvBaseTypedAdapter<VH: RvBaseTypedVH<TypedData<Int>>>: RecyclerView.Adapter<VH>() {

	protected var itemList = mutableListOf<TypedData<Int>>()

	fun setItems(elements: MutableList<TypedData<Int>>) {
		this.itemList = elements
	}

	override fun onBindViewHolder(holder: VH, position: Int) {
		holder.bind(itemList[position])
	}

	override fun getItemViewType(position: Int): Int = itemList[position].getType()
	override fun getItemCount(): Int = itemList.size
}