package com.example.grandmasicecream.data
import java.io.Serializable


data class IceCreamResponse(
    val iceCreams: List<IceCream>,
    val basePrice: Double
)

data class IceCream(
    val id: Long,
    val name: String,
    val status: String,
    val imageUrl: String?
) : Serializable
