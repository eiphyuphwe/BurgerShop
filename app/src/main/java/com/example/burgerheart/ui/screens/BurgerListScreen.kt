package com.example.burgerheart.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon

import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.burgerheart.MainActivity
import com.example.burgerheart.R
import com.example.burgerheart.model.Burger
import com.example.burgerheart.ui.viewmodels.BurgerViewModel
import com.google.gson.Gson

@Composable
fun BurgerListScreen(
    viewModel: BurgerViewModel, navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.getBurgerList()
    }
    val burgers by viewModel.burgers.collectAsState()
    BurgerList(navContrller = navController, burgers = burgers)

}

@Composable
fun BurgerList(
    navContrller: NavController, burgers: List<Burger>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Burger List",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(burgers) { burger ->
                BurgerListItem(burger = burger) {
                    navContrller.navigate("detail/${burger.id}")
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BurgerListItem(
    burger: Burger?, onItemClick: (Burger) -> Unit
) {
    if (burger != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    onItemClick.invoke(burger)
                }, // Navigate to BurgerDetail on item click
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
                //if coz of api usage limitation, if not, pls open the commented code and can use
                /*val burgerThumbnail =
                    burger.images?.get(0)?.sm ?: // if image is null then use default image
                    "https://s7d1.scene7.com/is/image/mcdonalds/t-mcdonalds-Bacon-Egg-Cheese-Biscuit-Regular-Size-Biscuit-1:product-header-desktop?wid=829&hei=455&dpr=off"*/
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
                    burger?.let {
                        Text(
                            text = it.name.toString(),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "$ ${burger.price}",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


