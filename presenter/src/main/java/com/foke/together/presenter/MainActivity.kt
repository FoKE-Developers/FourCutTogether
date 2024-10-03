package com.foke.together.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.foke.together.presenter.navigation.NavGraph
import com.foke.together.presenter.theme.FourCutTogetherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: temporary remove this option
        // enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    FourCutTogetherTheme {
        val navController = rememberNavController()
        NavGraph(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainScreen()
}