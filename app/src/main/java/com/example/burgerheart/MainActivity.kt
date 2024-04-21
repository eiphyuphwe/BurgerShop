package com.example.burgerheart

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.burgerheart.ui.screens.BurgerListScreen
import com.example.burgerheart.ui.screens.CheckOutScreen
import com.example.burgerheart.ui.screens.DetailScreen
import com.example.burgerheart.ui.viewmodels.BurgerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = BurgerViewModel()
        super.onCreate(savedInstanceState)
        setContent {
            BurgerShopApp(viewModel, context = applicationContext)
        }
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg.toString(), Toast.LENGTH_LONG).show()
    }
}


@Composable
fun BurgerShopApp(viewModel: BurgerViewModel, context: Context) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Burger Heart") },
                backgroundColor = Color.White,
                actions = {
                    IconButton(onClick = {
                        navController.navigate("checkout")
                    }) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Checkout"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                BottomNavigationItem(
                    selected = currentRoute == "menu",
                    onClick = {
                        navController.navigate("menu") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    },
                    label = {
                        Text("Menu")
                    }
                )

                BottomNavigationItem(
                    selected = currentRoute == "cart",
                    onClick = {
                        navController.navigate("cart") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    },
                    label = {
                        Text("Cart")
                    }
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "menu") {
            composable("menu") {
                BurgerListScreen(viewModel, navController = navController)
            }
            composable("cart") {
                CheckOutScreen(navController = navController, viewModel = viewModel, context)
            }
            composable("detail/{burgerId}") { backStackEntry ->
                val burgerId = backStackEntry.arguments?.getString("burgerId") ?: ""
                if (burgerId != "") {
                    viewModel.getBurgerDetailById(burgerId)
                    DetailScreen(context, viewModel = viewModel)
                }
            }
            composable("checkout") {
                CheckOutScreen(navController = navController, viewModel = viewModel, context)
            }
        }
    }

}


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Menu : Screen("menu", "Menu", Icons.Default.Menu)
    object Cart : Screen("cart", "Cart", Icons.Default.ShoppingCart)
}
