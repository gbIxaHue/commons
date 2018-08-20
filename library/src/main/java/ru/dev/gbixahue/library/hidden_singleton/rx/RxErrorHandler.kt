package ru.dev.gbixahue.library.hidden_singleton.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Anton Zhilenkov on 12.06.18.
 */
object RxErrorHandler {

	private val subject: PublishSubject<Throwable> = PublishSubject.create()

	fun observe(): Observable<Throwable> = subject.hide()

	fun handle(throwable: Throwable?) {
		throwable?.let { subject.onNext(it) }
	}
}