package com.foke.together.presenter.navigation

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.foke.together.presenter.screen.CameraScreen
import com.foke.together.presenter.screen.GenerateImageScreen
import com.foke.together.presenter.screen.HomeScreen
import com.foke.together.presenter.screen.InternalCameraScreen
import com.foke.together.presenter.screen.InternalCameraScreenRoot
import com.foke.together.presenter.screen.SelectFrameScreen
import com.foke.together.presenter.screen.SelectMethodScreen
import com.foke.together.presenter.screen.SettingScreen
import com.foke.together.presenter.screen.ShareScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    val permission = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(permission) {
        if(permission.status != PermissionStatus.Granted) {
            permission.launchPermissionRequest() // 권한 요청
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.path
    ) {
        addHomeScreen(navController, this)
        addSettingScreen(navController, this)
        addSelectFrameScreen(navController, this)
        addSelectMethodScreen(navController, this)
        addCameraScreen(navController, this)
        addInternalCameraScreen(navController, this)
        addGenerateImageScreen(navController, this)
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

private fun addSelectFrameScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.SelectFrame.path) {
        SelectFrameScreen(
            navigateToMethod = {
                navController.navigate(NavRoute.SelectMethod.path)
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
                navController.navigate(NavRoute.InternalCamera.path)
            },
            popBackStack = {
                navController.popBackStack(NavRoute.SelectFrame.path, inclusive = false)
            }
        )
    }
}

private fun addCameraScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Camera.path) {
        CameraScreen(
            navigateToGenerateImage = {
                navController.navigate(NavRoute.GenerateSingleRowImage.path)
            },
            popBackStack = {
                navController.popBackStack(NavRoute.Home.path, inclusive = false)
            }
        )
    }
}

private fun addInternalCameraScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.InternalCamera.path) {
        InternalCameraScreenRoot(
            navigateToGenerateImage = {
                navController.navigate(NavRoute.GenerateSingleRowImage.path)
            },
            popBackStack = {
                navController.popBackStack(NavRoute.Home.path, inclusive = false)
            }
        )
    }
}

private fun addGenerateImageScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.GenerateSingleRowImage.path) {
        GenerateImageScreen(
            navigateToShare = {
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
    navGraphBuilder.composable(route = NavRoute.Share.path) {
        ShareScreen(
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