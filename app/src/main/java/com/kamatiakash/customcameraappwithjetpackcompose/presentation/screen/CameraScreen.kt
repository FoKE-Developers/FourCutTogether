package com.kamatiakash.customcameraappwithjetpackcompose.presentation.screen

import android.widget.Toast
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.kamatiakash.customcameraappwithjetpackcompose.R
import com.kamatiakash.customcameraappwithjetpackcompose.presentation.MainViewModel
import com.kamatiakash.customcameraappwithjetpackcompose.presentation.viewmodel.CameraViewModel

@Composable
fun CameraScreen(
    navigationFrame: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val screeHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var previewView: PreviewView


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // we will show camera preview once permission is granted
            Box(modifier = Modifier
                .height(screeHeight * 0.85f)
                .width(screenWidth)) {
                AndroidView(
                    factory = {
                        previewView = PreviewView(it)
                        viewModel.showCameraPreview(previewView, lifecycleOwner)
                        previewView
                    },
                    modifier = Modifier
                        .height(screeHeight * 0.85f)
                        .width(screenWidth)
                )
            }

        Box(
            modifier = Modifier
                .height(screeHeight*0.15f),
            contentAlignment = Alignment.Center
        ){
            IconButton(onClick = {
            }) {

                Icon(painter =
                painterResource(id =
                R.drawable.ic_baseline_camera_alt_24),
                    contentDescription = "",
                    modifier = Modifier.size(45.dp),
                    tint = Color.Magenta
                )

            }
        }
    }
}