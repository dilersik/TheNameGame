package com.example.thenamegame.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thenamegame.R
import com.example.thenamegame.nav.ProfileNavEnum
import com.example.thenamegame.ui.theme.ButtonColor
import com.example.thenamegame.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun HomeView(navController: NavController) {
    val isClicked = remember { mutableStateOf(false) }
    val config = LocalConfiguration.current
    val imageBack: Int
    val imageAlign: Alignment
    val columnWidthPercentage: Float
    val rowHorizontal: Arrangement.Horizontal
    val columVertical: Arrangement.Vertical

    if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        imageBack = R.drawable.home_background_land
        imageAlign = Alignment.TopStart
        columnWidthPercentage = 0.4f
        rowHorizontal = Arrangement.End
        columVertical = Arrangement.Center
    } else {
        imageBack = R.drawable.home_background
        imageAlign = Alignment.Center
        columnWidthPercentage = 1f
        rowHorizontal = Arrangement.Start
        columVertical = Arrangement.Bottom
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Primary) {
        Image(
            painter = painterResource(imageBack),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            alignment = imageAlign
        )
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = rowHorizontal) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 40.dp)
                    .widthIn(0.dp, (config.screenWidthDp * columnWidthPercentage).dp),
                verticalArrangement = columVertical,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.home_text),
                    modifier = Modifier.padding(bottom = 20.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
                HomeButton(R.string.practice_mode, isClicked, enabled = !isClicked.value) {
                    navController.navigate(route = ProfileNavEnum.DETAIL.name + "/${ProfileNavEnum.MODE_PRACTICE}")
                }
                HomeButton(R.string.timed_mode, isClicked, enabled = !isClicked.value) {
                    navController.navigate(route = ProfileNavEnum.DETAIL.name + "/${ProfileNavEnum.MODE_TIMED}")
                }
            }
        }
    }
}

@Composable
private fun HomeButton(text: Int, isClicked: MutableState<Boolean>, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = {
            if (!isClicked.value) {
                isClicked.value = true
                onClick()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(bottom = 10.dp),
        colors = ButtonDefaults.buttonColors(ButtonColor),
        shape = RoundedCornerShape(14.dp),
        enabled = enabled
    ) {
        Text(text = stringResource(text), fontSize = 20.sp)
    }
    // Reset isClicked after a short delay to allow button clicks again
    LaunchedEffect(isClicked) {
        if (isClicked.value) {
            delay(1000)
            isClicked.value = false
        }
    }
}