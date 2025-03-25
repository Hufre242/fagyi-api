package com.example.grandmasicecream.ui.screens
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.grandmasicecream.data.IceCream
import com.example.grandmasicecream.viewmodel.CartViewModel
import com.example.grandmasicecream.viewmodel.IceCreamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IceCreamListScreen(
    viewModel: IceCreamViewModel,
    cartViewModel: CartViewModel,
    onIceCreamSelected: (IceCream) -> Unit,
    onCartClick: () -> Unit
) {
    val iceCreams by viewModel.iceCreams.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fagylaltok") },
                actions = {
                    BadgedBox(
                        badge = {
                            if (cartItems.isNotEmpty()) {
                                Badge { Text("${cartItems.size}") }
                            }
                        }
                    ) {
                        IconButton(onClick = onCartClick) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Kosár"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(iceCreams) { iceCream ->
                IceCreamItem(iceCream = iceCream, onClick = {
                    onIceCreamSelected(iceCream)
                })
            }
        }
    }
}

@Composable
fun IceCreamItem(
    iceCream: IceCream,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ){
        Row(modifier = Modifier.padding(8.dp)) {
            iceCream.imageUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Column {
                Text(iceCream.name, style = MaterialTheme.typography.titleMedium)
                Text(iceCream.status, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
