package com.foke.together.presenter.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.foke.together.presenter.screen.CameraScreen
import com.foke.together.presenter.screen.SelectFrameScreen
import com.foke.together.presenter.screen.HomeScreen
import com.foke.together.presenter.screen.SelectMethodScreen
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
        addSelectFrameScreen(navController, this)
        addSelectMethodScreen(navController, this)
        addCameraScreen(navController, this)
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
            navigationSelectFrame = {
                navController.navigate(NavRoute.SelectFrame.path)
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
            navigateToShare = {
                navController.navigate(NavRoute.Share.path)
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

private fun addSelectFrameScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.SelectFrame.path) {
        SelectFrameScreen(
            navigateToMethod = {
                navController.navigate(NavRoute.Camera.path)
            },
            popBackStack = {
                navController.popBackStack(NavRoute.Home.path, inclusive = false)
            }
        )
    }
}

private fun addSelectMethodScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.SelectMethod.path) {
        SelectMethodScreen(
            navigateToCamera = {
                navController.navigate(NavRoute.Camera.path)
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
    navGraphBuilder.composable(route = NavRoute.Share.path) {
        ShareScreen(
            popBackStack = {
                navController.popBackStack(NavRoute.Home.path, inclusive = false)
            }
        )
    }
}