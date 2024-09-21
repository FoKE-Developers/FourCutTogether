package com.foke.together

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.foke.together.presentation.MainScreen
import com.foke.together.ui.theme.SimpleNavComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNavComposeAppTheme {
                MainScreen()
            }
        }
    }
}


