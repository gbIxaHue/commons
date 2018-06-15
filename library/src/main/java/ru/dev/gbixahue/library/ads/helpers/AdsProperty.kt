package ru.dev.gbixahue.library.ads.helpers

import android.view.ViewGroup
import android.widget.FrameLayout
import ru.dev.gbixahue.library.predictable.Predictable

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
abstract class AdsProperty(
    val injectLayout: FrameLayout,
    val resizeLayout: ViewGroup,
    val adId: Int
): Predictable {
  abstract fun adsClicked()
}