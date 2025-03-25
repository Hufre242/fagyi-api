package com.example.grandmasicecream.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Body
import retrofit2.http.POST

interface IceCreamApi {
    @GET("icecreams.json")
    suspend fun getIceCreams(): IceCreamResponse

    @GET("extras.json")
    suspend fun getExtras(): List<ExtraGroup>

    companion object {
        fun create(): IceCreamApi {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder().addInterceptor(logger).build()
            return Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/udemx/hr-resources/master/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IceCreamApi::class.java)
        }
    }
}
