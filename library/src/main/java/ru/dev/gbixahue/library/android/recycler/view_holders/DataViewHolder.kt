package ru.dev.gbixahue.library.android.recycler.view_holders

import android.support.v7.widget.RecyclerView
import android.view.View
import ru.dev.gbixahue.library.android.recycler.DataBinder

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
abstract class DataViewHolder<D>(itemView: View): RecyclerView.ViewHolder(itemView), DataBinder<D>