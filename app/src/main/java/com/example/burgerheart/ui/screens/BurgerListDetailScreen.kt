package com.example.burgerheart.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.burgerheart.MainActivity
import com.example.burgerheart.R
import com.example.burgerheart.model.Burger
import com.example.burgerheart.ui.viewmodels.BurgerViewModel


// Define your detail screen composable
@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailScreen(activity: Context, viewModel: BurgerViewModel) {
    val item by viewModel.selectedBurger.collectAsState()
    if (item != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Display the image
            val burgerThumbnail = R.drawable.burger1
            //if coz of api usage limitation, if not, pls open the commented code and can use
            /* val burgerThumbnail =
                 item!!.images?.get(0)?.sm ?: // if image is null then use default image
                 "https://s7d1.scene7.com/is/image/mcdonalds/t-mcdonalds-Bacon-Egg-Cheese-Biscuit-Regular-Size-Biscuit-1:product-header-desktop?wid=829&hei=455&dpr=off"
            */
            val painter = rememberImagePainter(data = burgerThumbnail)
            Image(
                painter = painter, // Replace with actual image resource or URL
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display item name
            item?.name?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Display description
            item?.desc?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    maxLines = 3
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Display ingredients
            Text(
                text = "Ingredients:",
                fontSize = 16.sp,
                style = MaterialTheme.typography.h6,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ingredients: ${item?.ingredients?.joinToString(", ") { it.name }}",
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Display whether it's vegetarian or not

            Text(
                text = "Vegetarian: ${if (item?.veg == true) "Yes" else "No"}",
                fontSize = 16.sp,
                style = MaterialTheme.typography.h6,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Price: $ ${item!!.price}",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Quantity controls
            val quantity = remember { mutableStateOf(item?.quantity) }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    if (quantity.value != 0) {
                        quantity.value = quantity.value!! - 1
                    }
                }
                ) {
                    Text(
                        text = "-",
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = quantity.value.toString(), fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { quantity.value = quantity.value!! + 1 }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Add to cart button
            Button(
                onClick = {
                    item!!.quantity = quantity.value!!
                    viewModel.saveToBasket(item!!)
                    Toast.makeText(activity, "Item is added to the card", Toast.LENGTH_LONG).show()
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Add to Cart")
            }
        }
    }
}




