package com.teknokrait.mussichusettsapp.presenter

import com.teknokrait.mussichusettsapp.BuildConfig
import com.teknokrait.mussichusettsapp.model.RequestResult
import com.teknokrait.mussichusettsapp.model.Track
import com.teknokrait.mussichusettsapp.model.TrackItem
import com.teknokrait.mussichusettsapp.network.MusixmatchDataSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
class TracksPresenter(view: TracksPresenter.View) {

    private val composite = CompositeDisposable()

    private val view: View

    interface View {
        fun onSuccessGetTracks(trackList: List<Track>)
        fun onErrorGetTracks(throwable: Throwable)
    }

    init {
        this.view = view
    }

    fun getTracks(keyword: String, page: Int) {
        MusixmatchDataSource.getService()!!.getTracks(keyword, page, 4, "desc", BuildConfig.MUSIXMATCH_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RequestResult> {
                override fun onSubscribe(d: Disposable) {
                    composite.add(d)
                }

                override fun onNext(requestResult: RequestResult) {

                    Timber.e("Result 1 : $requestResult")
                    Timber.e("Result 2 : "+requestResult.message.body)
                    Timber.e("Result 3 : "+requestResult.message.body.tracks.toString())

                    val tracks = ArrayList<Track>()
                    if (requestResult.message.body.tracks != null){
                        for (item in requestResult.message.body.tracks){
                            tracks.add(item.track)
                        }
                    }
                    view.onSuccessGetTracks(tracks)

                }

                override fun onError(e: Throwable) {
                    view.onErrorGetTracks(e)
                }

                override fun onComplete() {

                }
            })
    }

    fun unsubscribe() {
        composite.dispose()
    }
}
