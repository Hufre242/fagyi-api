package com.example.grandmasicecream.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val logoId = context.resources.getIdentifier("logo", "drawable", context.packageName)
                        if (logoId != 0) {
                            Image(
                                painter = painterResource(id = logoId),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .height(40.dp)
                                    .padding(end = 8.dp)
                            )
                        }
                    }
                },
                actions = {
                    val cartIconId = context.resources.getIdentifier("cart_outline", "drawable", context.packageName)
                    BadgedBox(
                        badge = {
                            if (cartItems.isNotEmpty()) {
                                Badge { Text("${cartItems.size}") }
                            }
                        }
                    ) {
                        IconButton(onClick = onCartClick) {
                            if (cartIconId != 0) {
                                Icon(
                                    painter = painterResource(id = cartIconId),
                                    contentDescription = "Kosár",
                                    tint = Color.White
                                )
                            }
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
    val context = LocalContext.current
    val enabled = iceCream.status == "available"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable(enabled = enabled) { if (enabled) onClick() }
    ) {
        val painter = if (!iceCream.imageUrl.isNullOrBlank()) {
            rememberAsyncImagePainter(iceCream.imageUrl)
        } else {
            val placeholderId = context.resources.getIdentifier("placeholder", "drawable", context.packageName)
            if (placeholderId != 0) {
                painterResource(id = placeholderId)
            } else {
                null
            }
        }

        painter?.let {
            Image(
                painter = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color(0xFFCC0000))
                .fillMaxWidth()
                .padding(start = 12.dp, top = 12.dp, bottom = 8.dp, end = 12.dp)
        ) {
            Text(
                text = iceCream.name.uppercase(),
                color = Color.Yellow,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            val statusText = when (iceCream.status) {
                "available" -> "1 €"
                "melted" -> "Kifogyott"
                "unavailable" -> "Nem is volt"
                else -> iceCream.status
            }

            Text(
                text = statusText,
                color = Color.White,
                fontSize = 14.sp
            )

            Button(
                onClick = onClick,
                enabled = enabled,
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Red
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp)
            ) {
                Text("KOSÁRBA")
            }
        }
    }
}
