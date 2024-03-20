@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thenamegame.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.thenamegame.R
import com.example.thenamegame.model.Profile
import com.example.thenamegame.nav.ProfileNavEnum.Companion.MODE_PRACTICE
import com.example.thenamegame.ui.theme.CorrectAnswerColor
import com.example.thenamegame.ui.theme.Primary
import com.example.thenamegame.ui.theme.Secondary
import com.example.thenamegame.ui.theme.WrongAnswerColor

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

        Scaffold(topBar = { Toolbar(title, navController) },
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
                                ItemProfile(randomProfile, profile)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun ItemProfile(randomProfile: String, profile: Profile) {
    val context = LocalContext.current
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }
    var colorFilter by remember { mutableStateOf<ColorFilter?>(null) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(2.dp)
            .clickable {
                isAnswerCorrect = randomProfile == profile.getFullName()
                colorFilter = ColorFilter.tint(
                    if (isAnswerCorrect == true)
                        CorrectAnswerColor else WrongAnswerColor, BlendMode.Darken
                )
            },
        shape = RectangleShape,
    ) {
        Box(contentAlignment = Alignment.Center) {
            profile.headshot.url?.let {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(it.replace("https", "http"))
                        .crossfade(true)
                        .transformations(RoundedCornersTransformation())
                        .build()
                )
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth,
                    painter = painter,
                    contentDescription = "",
                    colorFilter = colorFilter
                )
                // Draw the mask image on top of the original image
                isAnswerCorrect?.let { correct ->
                    Image(
                        painter = painterResource(if (correct) R.drawable.correct_answer_icon else R.drawable.wrong_answer_icon),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
private fun Toolbar(title: String, navController: NavController) {
    TopAppBar(
        modifier = Modifier.shadow(elevation = 8.dp, spotColor = Color.DarkGray),
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