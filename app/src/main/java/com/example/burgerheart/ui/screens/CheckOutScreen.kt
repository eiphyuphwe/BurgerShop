package com.example.burgerheart.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.burgerheart.R
import com.example.burgerheart.model.Burger
import com.example.burgerheart.ui.viewmodels.BurgerViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CheckOutScreen(
    navController: NavController,
    viewModel: BurgerViewModel,
    context: Context
) {
    val checkOutItems by viewModel.checkOutItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Your Basket", style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(checkOutItems) { item ->
                    BasketItem(burger = item, context)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { Toast.makeText(context, "Processing....", Toast.LENGTH_LONG).show() },
                modifier = Modifier
                    .align(Alignment.End)
                    .size(width = 200.dp, 60.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Checkout")
            }
        }

    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BasketItem(burger: Burger?, context: Context) {
    if (burger != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rounded Rectangular Image View
                val burgerThumbnail = R.drawable.burger1
                /*val burgerThumbnail =
                    burger.images?.get(0)?.sm ?: // if image is null then use default image
                    "https://s7d1.scene7.com/is/image/mcdonalds/t-mcdonalds-Bacon-Egg-Cheese-Biscuit-Regular-Size-Biscuit-1:product-header-desktop?wid=829&hei=455&dpr=off"
              */
                val painter = rememberImagePainter(data = burgerThumbnail)

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Burger Name
                    burger?.name?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.h6,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    Text(
                        text = "Price : $ ${burger.price}",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Quantity Text
                        Text(
                            text = "qty : ${burger.quantity} : ",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.Black
                        )
                        val totalPrice = burger.price * burger.quantity
                        val formattedTotalPrice = String.format("%.2f", totalPrice)
                        Text(
                            text = "$ $formattedTotalPrice",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
