package ru.dev.gbixahue.library.android.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.AutoCompleteTextView

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
class DebounceAutocompleteView: AutoCompleteTextView {

  constructor (context: Context): super(context)
  constructor (context: Context, attrs: AttributeSet): super(context, attrs)
  constructor (context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)
//
//  private val publishSubject: PublishSubject<CharSequence> = PublishSubject.create<CharSequence>()
//  private var timeOut = 750L
//  private var minLength = 2
//
//  init {
//    publishSubject
//        .debounce(timeOut, TimeUnit.MILLISECONDS)
//        .filter { it.length > minLength }
//        .subscribe({
//          super.performFiltering(it, 0)
//        }, { rxError(this, it) })
//  }
//
//  override fun performFiltering(text: CharSequence, keyCode: Int) {
//    publishSubject.onNext(text)
//  }
//
//  fun changeTimeOut(timeOut: Long) {
//    this.timeOut = timeOut
//  }
//
//  fun setMinLength(length: Int) {
//    minLength = length
//  }
}
