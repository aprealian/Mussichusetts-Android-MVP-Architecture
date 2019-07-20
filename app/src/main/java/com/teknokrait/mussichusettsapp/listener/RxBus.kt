package com.teknokrait.mussichusettsapp.listener

import android.annotation.SuppressLint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import androidx.annotation.IntDef
import android.util.SparseArray
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer
import io.reactivex.disposables.Disposable

/**
 * Created by Aprilian Nur Wakhid Daini on 7/20/2019.
 */
object RxBus {

    private val sSubjectMap = SparseArray<PublishSubject<Any>>()
    private val sSubscriptionsMap = HashMap<Any, CompositeDisposable>()


    const val SUBJECT_MY_SUBJECT = 0
    const val SUBJECT_ANOTHER_SUBJECT = 1

//    @Retention(SOURCE)
    @IntDef(SUBJECT_MY_SUBJECT, SUBJECT_ANOTHER_SUBJECT)
    internal annotation class Subject

    /**
     * Get the subject or create it if it's not already in memory.
     */
    @SuppressLint("CheckResult")
    @NonNull
    private fun getSubject(@Subject subjectCode: Int): PublishSubject<Any> {
        var subject: PublishSubject<Any>? = sSubjectMap.get(subjectCode)
        if (subject == null) {
            subject = PublishSubject.create()
            subject.subscribeOn(AndroidSchedulers.mainThread())
            sSubjectMap.put(subjectCode, subject)
        }

        return subject
    }

    /**
     * Get the CompositeDisposable or create it if it's not already in memory.
     */
    @NonNull
    private fun getCompositeDisposable(@NonNull `object`: Any): CompositeDisposable {
        var compositeDisposable: CompositeDisposable? = sSubscriptionsMap.get(`object`)
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
            sSubscriptionsMap[`object`] = compositeDisposable
        }

        return compositeDisposable
    }

    /**
     * Subscribe to the specified subject and listen for updates on that subject. Pass in an object to associate
     * your registration with, so that you can unsubscribe later.
     * <br></br><br></br>
     * **Note:** Make sure to call [RxBus.unregister] to avoid memory leaks.
     */
    fun subscribe(@Subject subject: Int, @NonNull lifecycle: Any, @NonNull action: Consumer<Any>) {
        val disposable = getSubject(subject).subscribe(action)
        getCompositeDisposable(lifecycle).add(disposable)
    }


    /**
     * Unregisters this object from the bus, removing all subscriptions.
     * This should be called when the object is going to go out of memory.
     */
    fun unregister(@NonNull lifecycle: Any) {
        //We have to remove the composition from the map, because once you dispose it can't be used anymore
        val compositeDisposable = sSubscriptionsMap.remove(lifecycle)
        if (compositeDisposable != null) {
            compositeDisposable.dispose()
        }
    }

    /**
     * Publish an object to the specified subject for all subscribers of that subject.
     */
    fun publish(@Subject subject: Int, @NonNull message: Any) {
        getSubject(subject).onNext(message)
    }
}// hidden constructor