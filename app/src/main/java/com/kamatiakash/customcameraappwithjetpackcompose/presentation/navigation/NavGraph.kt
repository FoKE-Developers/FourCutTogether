package com.kamatiakash.customcameraappwithjetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kamatiakash.customcameraappwithjetpackcompose.presentation.screen.CameraScreen
import com.kamatiakash.customcameraappwithjetpackcompose.presentation.screen.FrameScreen
import com.kamatiakash.customcameraappwithjetpackcompose.presentation.screen.HomeScreen
import com.kamatiakash.customcameraappwithjetpackcompose.presentation.screen.SettingScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.path
    ) {
        addCameraScreen(navController, this)

        addHomeScreen(navController, this)

        addSettingScreen(navController, this)

        addFrameScreen(navController, this)
    }
}

private fun addCameraScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Camera.path) {
        CameraScreen(
            navigationFrame = {
                navController.navigate(NavRoute.Frame.path)
            },
            // camera로 돌아가기
            popBackStack = { navController.popBackStack() }

        )
    }
}

private fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Home.path) {
        HomeScreen(
            navigateToSetting = {
                navController.navigate(NavRoute.Setting.withArgs())
            },
            navigateToCamera = { query ->
                navController.navigate(NavRoute.Camera.withArgs(query))
            }
        )
    }
}

private fun popUpToHome(navController: NavHostController) {
    navController.popBackStack(NavRoute.Home.path, inclusive = false)
}

private fun addSettingScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Setting.path) {
        SettingScreen(
            popBackStack = { popUpToHome(navController) }
        )
    }
}

private fun addFrameScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Frame.path) {
        FrameScreen(
            popBackStack = { navController.popBackStack() },
            navigateToHome = {
                navController.popBackStack(NavRoute.Home.path, inclusive = false)
            }
        )
    }
}