package ru.dev.gbixahue.library.android.widget.recycler

import androidx.recyclerview.widget.RecyclerView
import ru.dev.gbixahue.library.android.widget.recycler.vh.RvBaseTypedVH
import ru.dev.gbixahue.library.arch.interfaces.TypedData

abstract class RvBaseTypedAdapter<VH: RvBaseTypedVH<TypedData<Int>>>: androidx.recyclerview.widget.RecyclerView.Adapter<VH>() {

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