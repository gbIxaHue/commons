package ru.dev.gbixahue.library.arch

import android.content.Context
import android.view.View
import io.reactivex.exceptions.CompositeException
import ru.dev.gbixahue.library.R
import ru.dev.gbixahue.library.extensions.components.stringFrom
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Created by Anton Zhilenkov on 15.11.18.
 */
open class CommonException(message: String? = ""): Throwable(message)

class EmptyListException: CommonException()

fun convertException(view: View, throwable: Throwable): String = convertException(view.context, throwable)

fun convertException(context: Context, throwable: Throwable): String {
	val errorConnectToServer = context.stringFrom(R.string.c_error_connection)
	return when (throwable) {
		is UnknownHostException -> errorConnectToServer
		is ConnectException -> errorConnectToServer
		is TimeoutException -> errorConnectToServer
		is SocketTimeoutException -> errorConnectToServer
		is IllegalStateException -> errorConnectToServer
		is EmptyListException -> context.stringFrom(R.string.c_error_empty_list)
		is CompositeException -> {
			var text = throwable.localizedMessage
			throwable.exceptions.forEach { text = convertException(context, it) }
			return text ?: errorConnectToServer
		}
		else -> throwable.localizedMessage ?: errorConnectToServer
	}
}