package com.ayprotech.spacex.data.network

import com.ayprotech.spacex.BuildConfig
import com.ayprotech.spacex.data.network.responses.launche.Launches
import com.ayprotech.spacex.data.network.responses.rocket.Rocket
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {
    @GET("launches")
    suspend fun getLaunches(): Launches

    @GET("rockets/{rocketId}")
    suspend fun getRocket(@Path("rocketId") rocketId: String): Rocket

}

private const val BASE_URL = "https://api.spacexdata.com/v4/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(OkHttpClient.Builder().also {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            it.addInterceptor(logging)
        }
    }.build())
    .build()


object Network {
    //    operator fun invoke(
//        networkConnectionInterceptor: NetworkConnectionInterceptor
//    ) : MyApi{
//
//        val okkHttpclient = OkHttpClient.Builder()
//            .addInterceptor(networkConnectionInterceptor)
//            .build()
//
//        return Retrofit.Builder()
//            .client(okkHttpclient)
//            .baseUrl()
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(MyApi::class.java)
//    }
    val retrofitService: MyApi by lazy { retrofit.create(MyApi::class.java) }
}