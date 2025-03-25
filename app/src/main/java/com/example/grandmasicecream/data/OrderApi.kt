package com.example.grandmasicecream.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {
    @POST("post")
    suspend fun submitOrder(@Body order: List<OrderItem>): retrofit2.Response<Any>

    companion object {
        fun create(): OrderApi {
            return Retrofit.Builder()
                .baseUrl("https://httpbin.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OrderApi::class.java)
        }
    }
}
