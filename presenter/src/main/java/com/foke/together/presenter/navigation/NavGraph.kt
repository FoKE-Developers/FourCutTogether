package com.foke.together.presenter.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.foke.together.presenter.screen.CameraScreen
import com.foke.together.presenter.screen.FrameScreen
import com.foke.together.presenter.screen.HomeScreen
import com.foke.together.presenter.screen.SettingScreen
import com.foke.together.presenter.screen.ShareScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.path
    ) {
        addHomeScreen(navController, this)
        addSettingScreen(navController, this)
        addCameraScreen(navController, this)
        addFrameScreen(navController, this)
        addShareScreen(navController, this)
    }
}


private fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Home.path) {
        HomeScreen(
            navigateToSetting = {
                navController.navigate(NavRoute.Setting.path)
            },
            navigateToCamera = {
                navController.navigate(NavRoute.Camera.path)
            },
            popBackStack = { navController.popBackStack() }
        )
    }
}

private fun addCameraScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Camera.path) {
        CameraScreen(
            navigateToFrame = {
                navController.navigate(NavRoute.Frame.path)
            },
            popBackStack = {
                navController.popBackStack(NavRoute.Home.path, inclusive = false)
            }
        )
    }
}

private fun addSettingScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Setting.path) {
        SettingScreen(
            popBackStack = {
                navController.popBackStack(NavRoute.Home.path, inclusive = false)
            }
        )
    }
}

private fun addFrameScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Frame.path) {
        FrameScreen(
            navigateShare = {
                navController.navigate(NavRoute.Share.path)
            },
            popBackStack = {
                navController.popBackStack(NavRoute.Home.path, inclusive = false)
            }
        )
    }
}

private fun addShareScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Frame.path) {
        ShareScreen(
            popBackStack = {
                navController.popBackStack(NavRoute.Home.path, inclusive = false)
            }
        )
    }
}