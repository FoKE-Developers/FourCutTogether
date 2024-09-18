package com.kamatiakash.customcameraappwithjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kamatiakash.customcameraappwithjetpackcompose.presentation.MainScreen
import com.kamatiakash.customcameraappwithjetpackcompose.ui.theme.SimpleNavComposeAppTheme
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


