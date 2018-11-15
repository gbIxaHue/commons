package ru.dev.gbixahue.library.android.widget.recycler.vh

import android.view.View
import ru.dev.gbixahue.library.arch.interfaces.TypedData

/**
 * Created by Anton Zhilenkov on 15.11.18.
 */
abstract class RvBaseTypedVH<D: Any>(
		itemView: View,
		private val type: Int
): RvBaseVH<TypedData<Int>>(itemView) {

	override fun bind(item: TypedData<Int>) {
		if (item.getType() == type) (item.data as? D)?.let { bindTypedData(it) }
	}

	abstract fun bindTypedData(data: D)
}