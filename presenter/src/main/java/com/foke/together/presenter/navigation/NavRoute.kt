package com.foke.together.presenter.navigation

sealed class NavRoute(val path: String) {

    object Camera: NavRoute("camera")

    object Home: NavRoute("home")

    object Frame: NavRoute("frame")
    object Share: NavRoute("share")
    object Setting: NavRoute("setting")

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