package ru.dev.gbixahue.library.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.dev.gbixahue.library.R
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.extensions.components.toast

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class MailTo(private val appName: String, private val mailTo: String, private val subject: String = "", private val body: String = "") {

	fun sendEmail(context: Context) {
		if (appName.isEmpty() || mailTo.isEmpty()) {
			Log.d(this, "Can't send email, $appName or $mailTo is empty")
			return
		}
		val builder = StringBuilder()
		builder.append("mailto:").append(Uri.encode(mailTo))
		if (subject.isNotEmpty()) {
			builder.append("?subject=").append("(").append(appName).append(") ").append(Uri.encode(subject))
			if (body.isNotEmpty()) {
				builder.append("&body=").append(Uri.encode(body))
			}
		}
		val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(builder.toString()))
		try {
			context.startActivity(intent)
		} catch (ex: ActivityNotFoundException) {
			context.toast(R.string.c_error_not_found_activity_email)
		}
	}
}
