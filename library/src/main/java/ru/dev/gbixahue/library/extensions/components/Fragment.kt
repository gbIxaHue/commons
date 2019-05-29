package ru.dev.gbixahue.library.extensions.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun androidx.fragment.app.Fragment.putString(key: String, value: Any?): androidx.fragment.app.Fragment {
	(value as? String)?.let { arguments = Bundle().apply { this.putString(key, value) } }
	return this
}

fun androidx.fragment.app.Fragment.putInt(key: String, value: Any?): androidx.fragment.app.Fragment {
	(value as? Int)?.let { arguments = Bundle().apply { this.putInt(key, value) } }
	return this
}

fun androidx.fragment.app.Fragment.putLong(key: String, value: Any?): androidx.fragment.app.Fragment {
	(value as? Long)?.let { arguments = Bundle().apply { this.putLong(key, value) } }
	return this
}

fun androidx.fragment.app.Fragment.putSerializable(key: String, value: Any?): androidx.fragment.app.Fragment {
	(value as? Serializable)?.let { arguments = Bundle().apply { this.putSerializable(key, value) } }
	return this
}

fun androidx.fragment.app.Fragment.put(key: String, value: Any?): androidx.fragment.app.Fragment {
//	(value as? Long)?.let { arguments = Bundle().apply { this.putLong(key, value) } }
	return this
}