package ru.dev.gbixahue.library.commons

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.dev.gbixahue.library.commons.input_widget.InputWidget

class MainActivity: AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar)

		fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}
	}

	fun onClick(view: View) {
		val view = findViewById<InputWidget>(R.id.iw1)
		view.isErrorEnabled = !view.isErrorEnabled
	}

}
