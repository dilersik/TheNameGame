package com.example.thenamegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.thenamegame.nav.Nav
import com.example.thenamegame.ui.theme.TheNameGameTheme
import com.example.thenamegame.ui.view.ProfilesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ProfilesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheNameGameTheme {
                Nav(viewModel)
            }
        }
    }
}