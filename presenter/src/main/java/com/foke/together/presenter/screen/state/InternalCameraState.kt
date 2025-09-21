package com.foke.together.presenter.screen.state

import androidx.compose.runtime.Stable

@Stable
data class InternalCameraState(
    val aspectRatio : Float = 1.5f,
    val cutCount : Int = 4,
)
