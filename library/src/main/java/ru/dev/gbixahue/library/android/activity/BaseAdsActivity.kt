package ru.dev.gbixahue.library.android.activity

import ru.dev.gbixahue.library.tools.ads.helpers.AdsProperty
import ru.dev.gbixahue.library.tools.analysis.ATrack
import ru.dev.gbixahue.library.tools.analysis.event.EPriority
import ru.dev.gbixahue.library.tools.analysis.event.HightEvent
import ru.dev.gbixahue.library.tools.analysis.event.TrackerEvent

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
open class BaseAdsActivity: BaseAppActivity() {

  protected fun initBottomAds(adsProperty: AdsProperty) {
    if (! adsProperty.predicate.performExpression()) return
//    BottomAdsView.inject(adsProperty.injectLayout, adsProperty.resizeLayout, adsProperty.adId).apply {
//      setOnAdClickListener {
//        BottomAdsView.restoreParentView(this, adsProperty.resizeLayout)
//        adsProperty.adsClicked()
//        sendEventOfClickingByAds()
//        destroy()
//      }
//    }
  }

  protected fun sendEventOfClickingByAds() {
    ATrack.send(TrackerEvent("Ads event", HightEvent.ADS_CLICKED.name, EPriority.HIGH))
  }
}