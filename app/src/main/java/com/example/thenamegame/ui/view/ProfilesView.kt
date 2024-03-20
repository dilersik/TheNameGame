@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thenamegame.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.thenamegame.R
import com.example.thenamegame.nav.ProfileNavEnum.Companion.MODE_PRACTICE
import com.example.thenamegame.ui.theme.Primary
import com.example.thenamegame.ui.theme.Secondary

@Composable
fun ProfilesView(viewModel: ProfilesViewModel, navController: NavController, mode: String?) {
    LaunchedEffect(key1 = true) {
        viewModel.getProfiles()
    }
    if (viewModel.data.value.loading == true) {
        CircularProgressBar()
    } else {
        val profiles = viewModel.data.value.data?.first ?: emptyList()
        val randomProfile = viewModel.data.value.data?.second ?: ""
        val title = stringResource(if (mode == MODE_PRACTICE) R.string.practice_mode else R.string.timed_mode)

        Scaffold(topBar = {
            TopAppBar(
                title = { Text(text = title) },
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
                        .padding(top = padding.calculateTopPadding(), start = 28.dp, end = 28.dp, bottom = 12.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(42.dp))
                        Text(text = randomProfile, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Primary)
                        Spacer(modifier = Modifier.height(20.dp))
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 164.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(profiles) { profile ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .padding(2.dp),
                                    shape = RectangleShape,
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
}

@Composable
fun CircularProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Primary
            )
        }
    }
}