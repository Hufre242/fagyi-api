package com.example.grandmasicecream.data

data class ExtraGroup(
    val type: String,
    val required: Boolean = false,
    val items: List<ExtraItem>
)

data class ExtraItem(
    val id: Long,
    val name: String,
    val price: Double
)
