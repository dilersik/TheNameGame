package com.example.thenamegame.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun HomeView(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = Primary) {
        Image(
            painter = painterResource(id = R.drawable.home_background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            verticalArrangement = Arrangement.Bottom,
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
            HomeButton(R.string.practice_mode_btn) {
                navController.navigate(route = ProfileNavEnum.DETAIL.name + "/${ProfileNavEnum.MODE_PRACTICE}")
            }
            HomeButton(R.string.timed_mode_btn) {
                navController.navigate(route = ProfileNavEnum.DETAIL.name + "/${ProfileNavEnum.MODE_TIMED}")
            }
        }
    }
}

@Composable
private fun HomeButton(text: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(bottom = 10.dp),
        colors = ButtonDefaults.buttonColors(ButtonColor),
        shape = RoundedCornerShape(14.dp),
    ) {
        Text(text = stringResource(text), fontSize = 20.sp)
    }
}