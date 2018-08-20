package ru.dev.gbixahue.library.arch.components.activity

import ru.dev.gbixahue.library.R
import ru.dev.gbixahue.library.android.activity.BaseActivity
import ru.dev.gbixahue.library.arch.OnBackPressHandler
import ru.dev.gbixahue.library.arch.routing.cicerone.Cicerone
import ru.dev.gbixahue.library.arch.routing.cicerone.Navigator
import ru.dev.gbixahue.library.arch.routing.cicerone.Router
import javax.inject.Inject

/**
 * Created by Anton Zhilenkov on 18.07.18.
 */
abstract class RouterActivity: BaseActivity() {

	@Inject
	internal lateinit var cicerone: Cicerone<Router>
	protected val router: Router by lazy { cicerone.router }

	protected abstract fun getNavigator(): Navigator

	override fun onResume() {
		super.onResume()
		cicerone.navigatorHolder.setNavigator(getNavigator())
	}

	override fun onPause() {
		cicerone.navigatorHolder.removeNavigator()
		super.onPause()
	}

	override fun onBackPressed() {
		val fragment = supportFragmentManager.findFragmentById(R.id.mainContentLayout) ?: return
		if ((fragment as? OnBackPressHandler)?.onBackPressed() == true) return
		cicerone.router.exit()
	}
}