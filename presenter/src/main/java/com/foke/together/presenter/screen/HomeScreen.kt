package com.foke.together.presenter.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.presenter.ui.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navigateToCamera: (String) -> Unit,
    navigateToSetting: (String) -> Unit,
    popBackStack: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val sampleData by viewModel.getSampleText().collectAsState()

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Home Screen", fontSize = 40.sp)

        Button(onClick = { navigateToCamera("camera") }){
            Text("Camera")
        }

        Button(onClick = { navigateToSetting("Setting") }){
            Text("Setting")
        }
        Text(text = sampleData.sampleText)
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
            HomeScreen(
                navigateToCamera = { },
                navigateToSetting = {},
                popBackStack = {}
            )
        }
    }
}