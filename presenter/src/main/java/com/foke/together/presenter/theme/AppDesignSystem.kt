package com.foke.together.presenter.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class AppColorScheme(
    val top : Color = Color.Unspecified,
    val bottom : Color = Color.Unspecified,
    val middle : Color = Color.Unspecified,

    val border : Color = Color.Unspecified,
    // 에러 컬러
    val tint : Color = Color.Unspecified,
)


data class  AppTypography(
    // Top App Bar Screen Name
    val head : TextStyle = TextStyle.Default,

    // 타이틀 바로 아래
    val title : TextStyle = TextStyle.Default,

    // 요소의 제목
    val body : TextStyle = TextStyle.Default,

    // 요소 제목의 하위 요소
    val label : TextStyle = TextStyle.Default,
)


data class AppShape(
    val container : Shape = RectangleShape,
    val surface : Shape = RectangleShape,
    val icon : Shape = RectangleShape,
    val button : Shape = RectangleShape,
)

data class AppSize(
    val layoutPadding : Dp = Dp.Unspecified,
    val buttonPadding : Dp = Dp.Unspecified,
    val icon  : Dp = Dp.Unspecified,
    val button : Dp = Dp.Unspecified,
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme()
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape()
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography()
}
val LocalAppSize = staticCompositionLocalOf {
    AppSize()
}