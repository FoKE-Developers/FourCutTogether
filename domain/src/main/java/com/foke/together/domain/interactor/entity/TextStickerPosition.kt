package com.foke.together.domain.interactor.entity

import androidx.annotation.FontRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

data class TextStickerPosition (
    val x: Int,
    val y: Int,
    @FontRes val font: Int,
    val color: Color,
    val backgroundColor: Color = Color(0,0,0,0),
    val fontWeight: FontWeight,
    val fontSize: Int,
    val rotation: Float = 0f
)