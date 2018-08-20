package ru.dev.gbixahue.library.tools.ads

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import ru.dev.gbixahue.library.android.activity.LifeCycleListener
import ru.dev.gbixahue.library.extensions.components.isLandscape
import ru.dev.gbixahue.library.extensions.components.isTablet
import ru.dev.gbixahue.library.extensions.views.enableLayoutAnimation

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */

//        32: device screen height <= 400
//        50: 400 < device screen height <= 720
//        90: device screen height > 720

//        320x50	Standard Banner	Phones and Tablets	BANNER
//        468x60	IAB Full-Size Banner	Tablets	FULL_BANNER
//        728x90	IAB Leaderboard	Tablets	LEADERBOARD
//        320x100	Large Banner	Phones and Tablets	LARGE_BANNER
//        300x250	IAB Medium Rectangle	Phones and Tablets	MEDIUM_RECTANGLE
//        Screen width x 32|50|90	Smart Banner	Phones and Tablets	SMART_BANNER

class BottomAdsView(context: Context, adId: Int): LifeCycleListener {

  var parentBottomPadding: Int = 0

  private var onAdClicked: (() -> Unit)? = null
  private var onAdLoaded: (() -> Unit)? = null

//  private var adView: AdView = AdView(context).apply {
//    adUnitId = context.resources.getString(adId)
//    id = ViewIdGenerator().generateViewId()
//  }
//  private val id: Int
//    get() = adView.id
//  private val measuredHeight: Int
//    get() {
//      adView.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
//          View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED))
//      return adView.measuredHeight
//    }

  init {
    if (context.isLandscape()) setSize(if (context.isTablet()) 2 else 6) else setSize(6)
//    adView.adListener = object: AdListener() {
//
//      override fun onAdLeftApplication() {
//        onAdClicked?.invoke()
//      }
//
//      override fun onAdLoaded() {
//        onAdLoaded?.invoke()
//      }
//    }
  }

  private fun setSize(iSize: Int) {
    when (iSize) {
//      1 -> adView.adSize = AdSize.BANNER
//      2 -> adView.adSize = AdSize.FULL_BANNER
//      3 -> adView.adSize = AdSize.LEADERBOARD
//      4 -> adView.adSize = AdSize.LARGE_BANNER
//      5 -> adView.adSize = AdSize.MEDIUM_RECTANGLE
//      6 -> adView.adSize = AdSize.SMART_BANNER
    }
  }

  fun setOnAdClickListener(callback: () -> Unit) {
    this.onAdClicked = callback
  }

  fun setOnAdLoadListener(callback: () -> Unit) {
    this.onAdLoaded = callback
  }

  fun setLayoutParams(params: ViewGroup.LayoutParams) {
//    adView.layoutParams = params
  }

  fun load() {
//    adView.loadAd(AdRequest.Builder().build())
  }

  fun destroy() {
//    adView.destroy()
  }

  override fun activityOnPause() {
//    adView.pause()
  }

  override fun activityResumed() {
//    adView.resume()
  }

  override fun activityOnDestroy() {
//    adView.destroy()
  }

  companion object {
    fun inject(parentView: FrameLayout, cuttingView: ViewGroup, adId: Int): BottomAdsView {
      cuttingView.enableLayoutAnimation()
      val banner = BottomAdsView(parentView.context, adId)
      banner.load()
      banner.setOnAdLoadListener {
        //        if (parentView.findViewById<AdView>(banner.id) != null) return@setOnAdLoadListener

        cutParentView(banner, cuttingView)
        val bannerParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        bannerParams.gravity = Gravity.BOTTOM
        banner.setLayoutParams(bannerParams)
//        parentView.addView(banner.adView)
      }
      return banner
    }

    fun cutParentView(banner: BottomAdsView, cuttingView: ViewGroup) {
      val context = cuttingView.context
      if (context.isTablet() && context.isLandscape()) return
      cuttingView.apply {
        banner.parentBottomPadding = paddingBottom
//        setPadding(paddingLeft, paddingTop, paddingRight, banner.measuredHeight)
      }
      cuttingView.requestLayout()
    }

    fun restoreParentView(banner: BottomAdsView, restoringView: ViewGroup) {
      val context = restoringView.context
      if (context.isTablet() && context.isLandscape()) return
      restoringView.apply { setPadding(paddingLeft, paddingTop, paddingRight, banner.parentBottomPadding) }
      restoringView.requestLayout()
    }
  }
}