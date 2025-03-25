package com.example.grandmasicecream.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.grandmasicecream.data.CartItem
import com.example.grandmasicecream.data.ExtraGroup
import com.example.grandmasicecream.data.OrderApi
import com.example.grandmasicecream.viewmodel.CartViewModel

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    extras: List<ExtraGroup>,
    basePrice: Double
) {
    val cartItems = cartViewModel.cartItems.collectAsState().value
    val context = LocalContext.current
    val orderApi = OrderApi.create()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Kosár", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(cartItems) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("🍨 ${item.iceCream.name}")
                        Text("➕ Extrák: ${item.selectedExtras.joinToString()}")

                        Button(
                            onClick = { cartViewModel.removeFromCart(index) },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Eltávolítás")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val totalPrice = cartItems.sumOf { item ->
            basePrice + item.selectedExtras.sumOf { id ->
                extras.flatMap { it.items }.find { it.id == id }?.price ?: 0.0
            }
        }

        Text(
            text = "Összesen: ${totalPrice} Ft",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.End)
        )

        Button(
            onClick = {
                cartViewModel.submitOrder(
                    api = orderApi,
                    onSuccess = {
                        Toast
                            .makeText(context, "✅ Rendelés sikeres!", Toast.LENGTH_SHORT)
                            .show()
                    },
                    onError = {
                        Toast
                            .makeText(context, "❌ Hiba: ${it.message}", Toast.LENGTH_LONG)
                            .show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Rendelés leadása")
        }
    }
}
