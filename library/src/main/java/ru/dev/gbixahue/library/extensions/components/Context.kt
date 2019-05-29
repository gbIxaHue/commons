package ru.dev.gbixahue.library.extensions.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Point
import android.net.Uri
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import ru.dev.gbixahue.library.android.log.Log
import ru.dev.gbixahue.library.hidden_singleton.handler.postUI
import java.lang.ref.WeakReference

/**
 * Created by Anton Zhilenkov on 12.09.17.
 */
fun Context.weakReference() = WeakReference<Context>(this)

fun Context.colorFrom(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)
fun Context.drawableFrom(@DrawableRes drawableId: Int) = AppCompatResources.getDrawable(this, drawableId)
fun Context.stringFrom(@StringRes stringId: Int) = resources.getString(stringId)
fun Context.dimenFrom(@DimenRes dimenId: Int) = resources.getDimension(dimenId)

fun Context.toast(messageResId: Int, length: Int = Toast.LENGTH_SHORT) {
	if (messageResId < 0) return
	toast(stringFrom(messageResId), length)
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
	Toast.makeText(this, message, length).show()
}

fun Context.dpToPx(dp: Float): Float = dp * resources.displayMetrics.density
fun Context.pxToDp(px: Float): Float = Math.round(px / resources.displayMetrics.density).toFloat()

fun Context.displayDensity(): Float {
	val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
	val metrics = DisplayMetrics()
	display.getMetrics(metrics)
	return metrics.density
}

fun Context.displayWidthInPx(): Float {
	val service = (getSystemService(Context.WINDOW_SERVICE) as? WindowManager) ?: return 0f
	val point = Point()
	service.defaultDisplay.getSize(point)
	return point.x.toFloat()
}

fun Context.displayHeightInPx(): Float {
	val service = (getSystemService(Context.WINDOW_SERVICE) as? WindowManager) ?: return 0f
	val point = Point()
	service.defaultDisplay.getSize(point)
	return point.y.toFloat()
}

fun Context.safeStartActivity(intent: Intent, failureCallback: (() -> Unit)? = null) {
	if (intent.resolveActivity(packageManager) != null) {
		postUI {
			ContextCompat.startActivity(this, intent, null)
		}
	} else {
		failureCallback?.invoke()
	}
}

fun Context.showInPlayMarket(appId: String, failureCallback: (() -> Unit)? = null) {
	val marketPage = Uri.parse("market://details?id=$appId")
	val intent = Intent(Intent.ACTION_VIEW, marketPage)
	intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
	safeStartActivity(intent, failureCallback)
}

fun Context.openWebView(url: String) {
	try {
		val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
		applicationContext.safeStartActivity(myIntent)
	} catch (e: ActivityNotFoundException) {
		e.printStackTrace()
		toast(e.localizedMessage)
	}
}

fun Context.isPortrait(): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
fun Context.getOrientation(): Int = resources.configuration.orientation
fun isPortrait(orientation: Int): Boolean = orientation == Configuration.ORIENTATION_PORTRAIT

fun Context.createOkCancelDialog(title: String, message: String,
		ok: DialogButton, cancel: DialogButton? = null, @StyleRes styleRes: Int? = null): AlertDialog {
	val builder = if (styleRes != null) AlertDialog.Builder(this, styleRes) else AlertDialog.Builder(this)
	return builder.apply {
		setTitle(title)
		setMessage(message)
		setPositiveButton(ok.name, { _, _ -> ok.callback() })
		cancel?.let { setNegativeButton(cancel.name, { _, _ -> it.callback() }) }
		setCancelable(false)
	}.create()
}

data class DialogButton(val name: String, val callback: () -> Unit)

fun <D> Context.packageInfo(defValue: D, infoGetter: (PackageInfo) -> D): D {
	return try {
		val packageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
		infoGetter(packageInfo)
	} catch (e: PackageManager.NameNotFoundException) {
		Log.e(this, e.message)
		defValue
	}
}

fun Context.isTablet(): Boolean = (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
		Configuration.SCREENLAYOUT_SIZE_LARGE

fun Context.isLandscape(): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE