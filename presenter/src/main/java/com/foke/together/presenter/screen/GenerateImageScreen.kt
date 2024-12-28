package com.foke.together.presenter.screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.presenter.R
import com.foke.together.presenter.frame.DefaultCutFrame
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.GenerateImageViewModel
import com.foke.together.util.AppLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GenerateImageScreen(
    navigateToShare: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: GenerateImageViewModel = hiltViewModel()
) {
    val graphicsLayer1 = rememberGraphicsLayer()
    val graphicsLayer2 = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()
    val isFirstState = remember { mutableStateOf(true) }

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "이미지를 생성중입니다",
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            modifier = Modifier
                .padding(bottom = 48.dp)
        )

        Row {
            if (isFirstState.value) {
                GetFrame(
                    cutFrame = viewModel.cutFrame,
                    imageUri = viewModel.imageUri,
                    graphicsLayer = graphicsLayer1,
                )
            } else {
                GetFrame(
                    cutFrame = viewModel.cutFrame,
                    imageUri = viewModel.imageUri,
                    graphicsLayer = graphicsLayer2,
                    isDual = true
                )
            }
        }

        CircularProgressIndicator(
            modifier = Modifier
                .width(128.dp)
                .height(128.dp)
                .padding(top = 48.dp),
            color = colorResource(R.color.app_primary_color),
            strokeWidth = 24.dp
        )
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        AppLog.d("GenerateSingleRowImageScreen", "LifecycleEventEffect: ON_RESUME", "${viewModel.imageUri}")
        coroutineScope.launch {
            isFirstState.value = true
            delay(200) // !!!!! TODO timing issue
            viewModel.generateImage1(graphicsLayer1)

            isFirstState.value = false
            delay(200) // !!!!! TODO timing issue
            viewModel.generateImage2(graphicsLayer2)

            delay(1000)
            navigateToShare()
        }
    }
}

// TODO: !!!!! left / right 20dp 마진 있었음
@Composable
fun GetFrame(
    cutFrame: CutFrame,
    imageUri: List<Uri>,
    graphicsLayer: GraphicsLayer,
    isDual: Boolean = false
) {
    Row(
        modifier = Modifier
            .drawWithContent {
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                drawLayer(graphicsLayer)
            }
    ) {
        DefaultCutFrame(cutFrame, imageUri)
        if (isDual) {
            DefaultCutFrame(cutFrame, imageUri)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultFrame() {
    FourCutTogetherTheme {
        GenerateImageScreen(
            navigateToShare = {},
            popBackStack = {}
        )
    }
}