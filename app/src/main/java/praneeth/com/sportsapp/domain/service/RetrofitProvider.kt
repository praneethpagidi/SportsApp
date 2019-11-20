package praneeth.com.sportsapp.domain.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Praneeth on 2019-11-18.
 */
const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"

class RetrofitProvider {
    companion object {
        fun create(): RetrofitService {
            //uncomment the below line to check the logs.
//            OkHttpClient.config(true)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.instance)
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }
}
