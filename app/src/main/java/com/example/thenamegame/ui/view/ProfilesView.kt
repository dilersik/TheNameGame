@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thenamegame.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.thenamegame.nav.ProfileNavEnum.Companion.MODE_TIMED
import com.example.thenamegame.ui.theme.Primary
import com.example.thenamegame.ui.theme.Secondary
import com.example.thenamegame.util.Constant.PROFILE_PER_TIME

@Composable
fun ProfilesView(viewModel: ProfilesViewModel, navController: NavController, mode: String?) {
    LaunchedEffect(key1 = true) {
        viewModel.getProfiles(mode)
    }
    val config = LocalConfiguration.current
    val loading = viewModel.loading.collectAsState().value
    val currentDataState = viewModel.currentData.collectAsState().value
    val finishedGame = viewModel.finishedGame.collectAsState().value

    if (finishedGame) ShowGameOver(viewModel, navController)
    if (loading) {
        CircularProgressBar()
    } else {
        val profiles = currentDataState.first
        val randomProfile = currentDataState.second

        Scaffold(topBar = { Toolbar(viewModel, mode, navController) },
            content = { padding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding(), start = 28.dp, end = 28.dp, bottom = 12.dp)
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            Column(
                                modifier = Modifier.weight(0.3f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextName(randomProfile)
                            }
                        }
                        Column(
                            modifier = Modifier.weight(0.7f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                                TextName(randomProfile)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 164.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(profiles) { profile ->
                                    ItemProfile(viewModel, mode, profile)
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
private fun TextName(randomProfile: String) {
    Spacer(modifier = Modifier.height(42.dp))
    Text(text = randomProfile, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Primary)
}

@Composable
private fun ShowGameOver(viewModel: ProfilesViewModel, navController: NavController) {
    val score = viewModel.count.collectAsState().value
    AlertDialog(
        onDismissRequest = {
            navController.popBackStack()
            viewModel.setFinishedGame(false)
        },
        title = { Text(stringResource(id = R.string.game_over_dialog_title)) },
        text = { Text(stringResource(R.string.game_over_dialog_text, score, viewModel.data.size / PROFILE_PER_TIME)) },
        confirmButton = {
            Button(onClick = {
                navController.popBackStack()
                viewModel.setFinishedGame(false)
            }) {
                Text("OK")
            }
        }
    )
}

@Composable
private fun ItemProfile(viewModel: ProfilesViewModel, mode: String?, profile: Profile) {
    val context = LocalContext.current
    val colorFilter = viewModel.colorFilter.collectAsState().value.second
    val selectedName = viewModel.colorFilter.collectAsState().value.first
    val maskImage = viewModel.maskImage.collectAsState().value.second
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(2.dp)
            .clickable(enabled = true) {
                viewModel.onProfileClick(profile.getFullName(), mode)
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
                val newColorFilter =
                    if (colorFilter != null && selectedName == profile.getFullName())
                        ColorFilter.tint(colorFilter, BlendMode.Darken)
                    else null
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth,
                    painter = painter,
                    contentDescription = "",
                    colorFilter = newColorFilter
                )
                // Draw the mask image on top of the original image
                if (selectedName == profile.getFullName()) {
                    maskImage?.let { maskImageRes ->
                        Image(
                            painter = painterResource(maskImageRes),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Toolbar(viewModel: ProfilesViewModel, mode: String?, navController: NavController) {
    val countDownTimer = viewModel.countdownTimer.collectAsState().value
    TopAppBar(
        modifier = Modifier.shadow(elevation = 8.dp, spotColor = Color.DarkGray),
        title = { Text(text = stringResource(if (mode == MODE_PRACTICE) R.string.practice_mode else R.string.timed_mode)) },
        navigationIcon = {
            IconButton({
                viewModel.setFinishedGame(false)
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "menu items"
                )
            }
        },
        actions = {
            if (mode == MODE_TIMED) {
                CircularProgressIndicator(
                    progress = { countDownTimer.toFloat() },
                    color = Color.White,
                    trackColor = Primary
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