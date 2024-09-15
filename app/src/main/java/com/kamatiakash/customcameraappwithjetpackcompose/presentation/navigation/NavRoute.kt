package com.kamatiakash.customcameraappwithjetpackcompose.presentation.navigation

sealed class NavRoute(val path: String) {

    object Setting: NavRoute("setting")

    object Home: NavRoute("home")

    object Frame: NavRoute("profile") {
        val id = "id"
        val showDetails = "showDetails"
    }

    object Camera: NavRoute("camera") {
        val query = "query"
    }

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}