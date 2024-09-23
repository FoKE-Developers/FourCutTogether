package com.foke.together.presenter.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.foke.together.presenter.ui.theme.FourCutTogetherTheme

@Composable
fun CameraScreen(
    navigateToFrame: () -> Unit,
    popBackStack: () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Camera Screen", fontSize = 40.sp)
        Button(onClick = navigateToFrame){
            Text("Frame")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FourCutTogetherTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CameraScreen(
                navigateToFrame = { },
                popBackStack = { }
            )
        }
    }
}