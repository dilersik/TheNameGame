@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thenamegame.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfilesView(navController: NavController, mode: String?) {

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = mode.toString()) },
            navigationIcon = {
                IconButton({
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "menu items"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
        )
    },
        content = { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    MovieRow(movie = movie)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow {
//                        items(listOf()) {
//                            Card(
//                                modifier = Modifier
//                                    .size(340.dp)
//                                    .padding(6.dp),
//                                elevation = CardDefaults.cardElevation(6.dp),
//                            ) {
//                                val painter = rememberAsyncImagePainter(
//                                    model = ImageRequest.Builder(LocalContext.current)
//                                        .data(it)
//                                        .crossfade(true)
//                                        .transformations(RoundedCornersTransformation())
//                                        .build(),
//                                )
//                                Image(
//                                    modifier = Modifier.fillMaxSize(),
//                                    contentScale = ContentScale.FillWidth,
//                                    painter = painter,
//                                    contentDescription = ""
//                                )
//                            }
//                        }
                    }
                }
            }
        }
    )

}