package com.foke.together.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.foke.together.ui.theme.SimpleNavComposeAppTheme

@Composable
fun HomeScreen(
    navigateToSetting: () -> Unit,
    navigateToCamera: (String) -> Unit
) {

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Home Screen", fontSize = 40.sp)

        DefaultButton(
            text = "Setting",
            onClick = { navigateToSetting() }
        )

        DefaultButton(
            text = "Tap To Start",
            onClick = { navigateToCamera("PhoneCamera") }
        )
    }
}

@Composable
fun DefaultButton(text: String, onClick: () -> Unit) {

}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    SimpleNavComposeAppTheme(useSystemUiController = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(
                navigateToCamera = { _ -> },
                navigateToSetting = {}
            )
        }
    }
}