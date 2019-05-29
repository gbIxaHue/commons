package ru.dev.gbixahue.library.android.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import ru.dev.gbixahue.library.R
import ru.dev.gbixahue.library.extensions.views.stringFrom

/**
 * Created by Anton Zhilenkov on 20.08.18.
 */
class SimpleAlertDialog(
		context: Context,
		private val titleId: Int
) {

	private val builder = AlertDialog.Builder(context)

	private val mainView: View = LayoutInflater.from(context).inflate(R.layout.dlg_simple, null)
	private val tvTitle: TextView = mainView.findViewById(R.id.tv_title)
	private val btnPositive: Button = mainView.findViewById(R.id.btn_positive)
	private val btnNegative: Button = mainView.findViewById(R.id.btn_negative)

	private lateinit var dialog: AlertDialog

	fun setPositive(nameId: Int, callback: (() -> Unit)? = null) {
		btnPositive.text = btnPositive.stringFrom(nameId)
		btnPositive.setOnClickListener {
			dialog.dismiss()
			callback?.invoke()

		}
	}

	fun setNegative(nameId: Int, callback: (() -> Unit)? = null) {
		btnNegative.text = btnPositive.stringFrom(nameId)
		btnNegative.setOnClickListener {
			dialog.dismiss()
			callback?.invoke()
		}
	}

	fun show() {
		tvTitle.text = tvTitle.stringFrom(titleId)
		builder.setView(mainView)
		dialog = builder.show()
	}
}