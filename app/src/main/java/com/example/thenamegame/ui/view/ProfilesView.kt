@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thenamegame.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.thenamegame.ui.theme.Secondary

@Composable
fun ProfilesView(viewModel: ProfilesViewModel, navController: NavController, mode: String?) {
    val profiles = viewModel.data.value.data?.toList() ?: emptyList()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = mode.toString()) },
            navigationIcon = {
                IconButton({ navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "menu items"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                titleContentColor = Color.White,
                containerColor = Secondary,
                navigationIconContentColor = Color.White
            ),
        )
    },
        content = { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(profiles) { profile ->
                            Card(
                                modifier = Modifier
                                    .height(150.dp)
                                    .padding(6.dp)
                                    .weight(.5f),
                                elevation = CardDefaults.cardElevation(6.dp),
                            ) {
                                profile.headshot.url?.let {
                                    val painter = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(it.replace("https", "http"))
                                            .crossfade(true)
                                            .transformations(RoundedCornersTransformation())
                                            .build(),
                                    )
                                    Image(
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.FillWidth,
                                        painter = painter,
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )

}