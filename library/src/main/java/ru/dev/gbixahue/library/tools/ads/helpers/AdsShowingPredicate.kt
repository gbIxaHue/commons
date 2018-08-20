package ru.dev.gbixahue.library.tools.ads.helpers

import android.view.ViewGroup
import android.widget.FrameLayout
import ru.dev.gbixahue.library.android.preference.Key
import ru.dev.gbixahue.library.android.preference.PreferenceRepository
import ru.dev.gbixahue.library.extensions.randomRange
import ru.dev.gbixahue.library.tools.predictable.Predicate
import ru.dev.gbixahue.library.utils.incrementTime
import java.util.*

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class AdsShowingPredicate(
		injectLayout: FrameLayout,
		resizeLayout: ViewGroup,
		adId: Int,
		private val prefRepo: PreferenceRepository
): AdsProperty(injectLayout, resizeLayout, adId) {

	override val predicate: Predicate
		get() = getPrediction()

	/**
	 * return true if the prediction success and need to performExpression an action
	 */
	private fun getPrediction(): Predicate {
		return object: Predicate {
			override fun performExpression(): Boolean {
				if (! prefRepo.has(Key.AD_HIDE_PRIOR)) return true

				val hideAdPriorTime = prefRepo.restore(Key.AD_HIDE_PRIOR, - 1L)
				if (hideAdPriorTime < System.currentTimeMillis()) {
					prefRepo.remove(Key.AD_HIDE_PRIOR)
					return true
				}
				return false
			}
		}
	}

	override fun adsClicked() {
		val hidePrior = incrementTime(Calendar.HOUR, System.currentTimeMillis(), randomRange(3, 6))
		prefRepo.save(Key.AD_HIDE_PRIOR, hidePrior)
	}
}