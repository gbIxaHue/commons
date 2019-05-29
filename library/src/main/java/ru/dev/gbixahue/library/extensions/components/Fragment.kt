package ru.dev.gbixahue.library.extensions.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun Fragment.putString(key: String, value: Any?): Fragment {
	(value as? String)?.let { arguments = Bundle().apply { this.putString(key, value) } }
	return this
}

fun Fragment.putInt(key: String, value: Any?): Fragment {
	(value as? Int)?.let { arguments = Bundle().apply { this.putInt(key, value) } }
	return this
}

fun Fragment.putLong(key: String, value: Any?): Fragment {
	(value as? Long)?.let { arguments = Bundle().apply { this.putLong(key, value) } }
	return this
}

fun Fragment.putSerializable(key: String, value: Any?): Fragment {
	(value as? Serializable)?.let { arguments = Bundle().apply { this.putSerializable(key, value) } }
	return this
}

fun Fragment.put(key: String, value: Any?): Fragment {
//	(value as? Long)?.let { arguments = Bundle().apply { this.putLong(key, value) } }
	return this
}