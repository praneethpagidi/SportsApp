package praneeth.com.sportsapp.domain.service

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import praneeth.com.sportsapp.BuildConfig
import java.util.concurrent.TimeUnit

/**
 * Created by Praneeth on 2019-11-18.
 */
object OkHttpClient {
    private val instanceDelegate = lazy {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.callTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    /**
     * A singleton instance of the OkHttpClient object.
     */
    val instance by instanceDelegate
}
