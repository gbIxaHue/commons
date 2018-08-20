package ru.dev.gbixahue.library.tools.ads

import android.content.Context
import ru.dev.gbixahue.library.arch.OnBackPressHandler
import ru.dev.gbixahue.library.hidden_singleton.handler.postWork
import java.lang.ref.WeakReference

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class BackPressInterstitialAd(private val wContext: WeakReference<Context>, private val adId: Int): OnBackPressHandler {

	//  private var inAd: InterstitialAd? = null
	private var onAdClickListener: (() -> Unit)? = null

	fun init(listener: (() -> Unit)? = null) {
		onAdClickListener = listener
		wContext.get()?.let {
			postWork({
				//        inAd = InterstitialAd(it).apply {
//          adUnitId = it.stringFrom(adId)
//          if (it is AppCompatActivity) {
//            it.runOnUiThread { loadAd(AdRequest.Builder().build()) }
//          }
//        }
//        inAd?.adListener = object: AdListener() {
//          override fun onAdLeftApplication() {
//            onAdClickListener?.invoke()
//          }
//        }
			})
		}

	}

	override fun onBackPressed(): Boolean {
//    inAd?.let { if (it.isLoaded) it.show() }
		return false
	}
}