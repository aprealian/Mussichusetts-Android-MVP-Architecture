package com.teknokrait.mussichusettsapp.network

import com.teknokrait.mussichusettsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
object MusixmatchDataSource {
    private var service: MusixmatchService? = null

    fun getService(): MusixmatchService? {
        if (service == null) {
            val builder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

            val okHttpClientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(loggingInterceptor)
            }
            val okHttpClient = okHttpClientBuilder
                .readTimeout(25, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .header("Authorization", BuildConfig.MUSIXMATCH_API_KEY)
                            .build()
                    )
                }
                .build()
            val retrofit = builder.baseUrl(BuildConfig.URL).client(okHttpClient).build()
            service = retrofit.create(MusixmatchService::class.java)
        }
        return service
    }

}