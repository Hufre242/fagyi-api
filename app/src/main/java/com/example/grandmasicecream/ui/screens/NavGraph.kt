package com.example.grandmasicecream.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.grandmasicecream.data.CartItem
import com.example.grandmasicecream.viewmodel.CartViewModel
import com.example.grandmasicecream.viewmodel.ExtrasViewModel
import com.example.grandmasicecream.viewmodel.IceCreamViewModel
import com.example.grandmasicecream.ui.screens.*

@Composable
fun AppNavGraph(
    navController: NavHostController,
    iceCreamViewModel: IceCreamViewModel,
    extrasViewModel: ExtrasViewModel,
    cartViewModel: CartViewModel
) {
    NavHost(navController = navController, startDestination = "iceCreamList") {

        composable("iceCreamList") {
            IceCreamListScreen(
                viewModel = iceCreamViewModel,
                cartViewModel = cartViewModel,
                onIceCreamSelected = { iceCream ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("selectedIceCream", iceCream)
                    navController.navigate("extras")
                },
                onCartClick = {
                    navController.navigate("cart")
                }
            )
        }

        composable("extras") {
            val extras by extrasViewModel.extras.collectAsState()
            val selectedIceCream =
                navController.previousBackStackEntry?.savedStateHandle?.get<com.example.grandmasicecream.data.IceCream>("selectedIceCream")

            if (selectedIceCream != null) {
                ExtraSelectionScreen(
                    extraGroups = extras,
                    onConfirm = { selectedExtraIds ->
                        cartViewModel.addToCart(
                            CartItem(
                                iceCream = selectedIceCream,
                                selectedExtras = selectedExtraIds
                            )
                        )
                        navController.navigate("cart")
                    }
                )
            }
        }

        composable("cart") {
            val extras by extrasViewModel.extras.collectAsState()
            val basePrice = 1.0

            CartScreen(
                cartViewModel = cartViewModel,
                extras = extras,
                basePrice = basePrice
            )
        }
    }
}
