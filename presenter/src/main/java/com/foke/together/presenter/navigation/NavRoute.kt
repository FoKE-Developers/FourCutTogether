package com.foke.together.presenter.navigation

sealed class NavRoute(val path: String) {

    object Home: NavRoute("home")
    object Setting: NavRoute("setting")
    object SelectFrame: NavRoute("select_frame")
    object SelectMethod: NavRoute("select_method")
    object Camera: NavRoute("camera")
    object GenerateSingleRowImage: NavRoute("generate_single_row_image")
    object GenerateTwoRowImage: NavRoute("generate_two_row_image")
    object Share: NavRoute("share")

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }
}