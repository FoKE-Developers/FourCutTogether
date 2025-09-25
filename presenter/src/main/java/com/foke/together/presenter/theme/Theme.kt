package com.foke.together.presenter.theme
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp


private val topColorScheme = AppColorScheme(
    top = topColor,
    bottom = bottomColor,
    middle = middleColor,
    border = borderColor,
    tint = tintColor
)

private val bottomColorScheme = AppColorScheme(
    top = bottomColor,
    bottom = topColor,
    middle = middleColor,
    border = tintColor,
    tint = borderColor
)
@Composable
fun FourCutTogetherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if(darkTheme) bottomColorScheme else topColorScheme
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppShape provides AppShape(
            container = container,
            surface = surface,
            icon = icon,
            button = button
        ),
        LocalAppTypography provides localTypography,
        LocalAppSize provides AppSize(
            layoutPadding = 24.dp,
            buttonPadding = 12.dp,
            icon = 36.dp,
            button = 32.dp
        ),
        LocalIndication provides ripple(),
        content = content,
    )
}

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable
        get() = LocalAppColorScheme.current

    val typography : AppTypography
        @Composable
        get() = LocalAppTypography.current

    val shapes : AppShape
        @Composable
        get() = LocalAppShape.current

    val size : AppSize
        @Composable
        get() = LocalAppSize.current
}