package com.example.grandmasicecream.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grandmasicecream.data.CartItem
import com.example.grandmasicecream.data.OrderApi
import com.example.grandmasicecream.data.OrderItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(item: CartItem) {
        _cartItems.value = _cartItems.value + item
    }

    fun removeFromCart(index: Int) {
        _cartItems.value = _cartItems.value.toMutableList().apply {
            removeAt(index)
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun submitOrder(
        api: OrderApi,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val orderItems = _cartItems.value.map {
                    OrderItem(
                        id = it.iceCream.id,
                        extra = it.selectedExtras
                    )
                }

                val response = api.submitOrder(orderItems)

                if (response.isSuccessful) {
                    onSuccess()
                    clearCart()
                } else {
                    onError(Exception("Hiba: ${response.code()}"))
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
