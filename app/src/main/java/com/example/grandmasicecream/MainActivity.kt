package com.example.grandmasicecream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.grandmasicecream.ui.AppNavGraph
import com.example.grandmasicecream.ui.theme.GrandmasIceCreamTheme
import com.example.grandmasicecream.viewmodel.CartViewModel
import com.example.grandmasicecream.viewmodel.ExtrasViewModel
import com.example.grandmasicecream.viewmodel.IceCreamViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GrandmasIceCreamTheme {
                val navController = rememberNavController()
                val iceCreamViewModel = remember { IceCreamViewModel() }
                val extrasViewModel = remember { ExtrasViewModel() }
                val cartViewModel = remember { CartViewModel() }

                AppNavGraph(
                    navController = navController,
                    iceCreamViewModel = iceCreamViewModel,
                    extrasViewModel = extrasViewModel,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}
