package ru.dev.gbixahue.library.android.dialog

import android.content.Context
import android.support.v7.app.AlertDialog


/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class SingleChoiceDialog(private val context: Context, private val titleId: Int, private val arrayOfChoice: Array<CharSequence>) {

  var themeId: Int = - 1

  fun show(onItemSelected: (Int) -> Unit) {
    val builder = getBuilder()
    builder.setTitle(titleId)
    builder.setItems(arrayOfChoice, { dialog, which ->
      onItemSelected(which)
    })
    builder.create().show()
  }

  private fun getBuilder(): AlertDialog.Builder = if (themeId > 0) AlertDialog.Builder(context, themeId) else AlertDialog.Builder(context)
}