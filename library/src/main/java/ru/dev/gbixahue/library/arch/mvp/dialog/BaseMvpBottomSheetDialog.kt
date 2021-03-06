package ru.dev.gbixahue.library.arch.mvp.dialog

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.view.View
import ru.dev.gbixahue.library.android.LayoutHolder
import ru.dev.gbixahue.library.arch.OnBackPressHandler

/**
 * Created by Anton Zhilenkov on 21.08.18.
 */
abstract class BaseMvpBottomSheetDialog(
		context: Context
): BottomSheetDialog(context), MvpDlgView, LayoutHolder {

	protected val presenter: MvpDlgPresenter<MvpDlgView>
	protected val mainView: View

	init {
		initComponent()
		presenter = getMvpPresenter()
		presenter.attached(this)
		mainView = layoutInflater.inflate(getLayoutId(), null, false)
		setContentView(mainView)
		presenter.contentViewSet()
	}

	abstract fun initComponent()
	abstract fun getMvpPresenter(): MvpDlgPresenter<MvpDlgView>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		presenter.created()
	}

	override fun onStart() {
		super.onStart()
		presenter.started()
	}

	override fun show() {
		super.show()
		presenter.showed()
	}

	override fun onStop() {
		super.onStop()
		presenter.stopped()
	}

	override fun dismiss() {
		super.dismiss()
		presenter.dismissed()
	}

	override fun cancel() {
		super.cancel()
		presenter.cancelled()
		presenter.detached()
	}

	override fun onBackPressed() {
		super.onBackPressed()
		(presenter as? OnBackPressHandler)?.onBackPressed()
	}

	protected fun <T: View> bindView(id: Int): T {
		return mainView.findViewById(id) as T
	}
}