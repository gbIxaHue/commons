package ru.dev.gbixahue.library.android.extensions.components

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.widget.Toast
import ru.dev.gbixahue.library.android.log.Log
import java.io.IOException

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
fun Context.colorFrom(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

fun Context.drawableFrom(@DrawableRes drawableId: Int) = ContextCompat.getDrawable(this, drawableId)
fun Context.stringFrom(@StringRes stringId: Int) = resources.getString(stringId)
fun Context.dimenFrom(@DimenRes dimenId: Int) = resources.getDimension(dimenId)

fun Context.showToast(messageResId: Int) {
  showToast(resources.getString(messageResId))
}

fun Context.showToast(message: String) {
  if (message.isBlank()) return
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun <D> Context.packageInfo(defValue: D, infoGetter: (PackageInfo) -> D): D {
  return try {
    val packageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
    infoGetter(packageInfo)
  } catch (e: PackageManager.NameNotFoundException) {
    Log.e(this, e.message)
    defValue
  }
}

fun Context.isTablet(): Boolean {
  return (getResources().getConfiguration().screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
      Configuration.SCREENLAYOUT_SIZE_LARGE;
}

fun Context.isLandscape(): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun Context.getListOfFilesFromAssetsFolder(folderName: String): List<String> {
  return try {
    assets.list(folderName).asList()
  } catch (e: IOException) {
    e.printStackTrace()
    ArrayList()
  }
}