package com.teknokrait.mussichusettsapp.network

import com.teknokrait.mussichusettsapp.model.RequestResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
interface MusixmatchService {

    @GET("track.search")
    fun getTracks(
        @Query("q_artist") artist: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("s_track_rating") trackRating: String,
        @Query("apikey") apiKey: String): Observable<RequestResult>

}