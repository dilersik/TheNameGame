package com.example.thenamegame.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.thenamegame.ui.view.HomeView
import com.example.thenamegame.ui.view.ProfilesView
import com.example.thenamegame.ui.view.ProfilesViewModel

@Composable
fun Nav(viewModel: ProfilesViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ProfileNavEnum.HOME.name) {
        composable(ProfileNavEnum.HOME.name) {
            HomeView(navController)
        }

        composable(
            ProfileNavEnum.DETAIL.name + "/{${ProfileNavEnum.PARAM_NAME_MODE}}",
            arguments = listOf(navArgument(name = ProfileNavEnum.PARAM_NAME_MODE) {
                type = NavType.StringType
            })
        ) {
            ProfilesView(viewModel, navController, mode = it.arguments?.getString(ProfileNavEnum.PARAM_NAME_MODE))
        }
    }
}