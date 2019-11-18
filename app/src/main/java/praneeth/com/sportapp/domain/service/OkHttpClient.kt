package praneeth.com.sportapp.domain.service

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by Praneeth on 2019-11-18.
 */
object OkHttpClient {
    private var logging = false
    private val instanceDelegate = lazy {
        OkHttpClient.Builder().apply {
            if (logging) {
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

    /**
     * Initializes config indicates whether to enable logging for network request. Only before
     * the first instance is created, this config can be changed, after which calling this will do nothing.
     *
     * @param enableLogging boolean value indicate whether to enable logging; default value is false
     */
    fun config(enableLogging: Boolean = false) {
        if (instanceDelegate.isInitialized()) {
            Log.e("OkHttpClient","Can't reconfigure OkHttpClient after initialization")
            return
        }

        logging = enableLogging
    }
}