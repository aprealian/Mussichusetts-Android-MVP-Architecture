package com.teknokrait.mussichusettsapp.listener

import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

/**
 * Created by Aprilian Nur Wakhid Daini on 7/20/2019.
 */
object RxBusOld {

    private val sSubject = PublishSubject.create<Any>()

    fun subscribe(@NonNull action: Consumer<Any>): Disposable {
        return sSubject.subscribe(action)
    }

    fun publish(@NonNull message: Any) {
        sSubject.onNext(message)
    }
}