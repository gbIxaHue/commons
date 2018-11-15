package ru.dev.gbixahue.library.utils

import android.content.Context
import android.content.Intent
import ru.dev.gbixahue.library.R
import ru.dev.gbixahue.library.extensions.components.stringFrom

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class ShareMe() {

	companion object {

		const val linkToGoogleApp = "https://play.google.com/store/apps/details?id="

		fun toFriends(context: Context, message: String) {
			val sharingIntent = Intent(Intent.ACTION_SEND)
			sharingIntent.type = "text/plain"
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "\n\n")
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message + " $linkToGoogleApp" + context.applicationInfo.packageName)
			context.startActivity(Intent.createChooser(sharingIntent, context.stringFrom(R.string.c_share)))
		}
	}
}