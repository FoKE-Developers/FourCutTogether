package com.foke.together.presenter.screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foke.together.domain.interactor.entity.DefaultCutFrameSet
import com.foke.together.presenter.component.AppTopBar
import com.foke.together.presenter.component.BasicScaffold
import com.foke.together.presenter.frame.DefaultCutFrame
import com.foke.together.presenter.theme.AppTheme
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
    val TAG = "GenerateImageScreen"
    val cutFrame = viewModel.cutFrame as DefaultCutFrameSet
    val graphicsLayer1 = rememberGraphicsLayer()
    val graphicsLayer2 = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()
    val isFirstState = remember { mutableStateOf(true) }
    val imageUri by viewModel.imageUri.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        AppLog.d(TAG, "LifecycleEventEffect: ON_RESUME", "${viewModel.imageUri}")
        coroutineScope.launch {
            isFirstState.value = true
            delay(200) // !!!!! TODO. timing issue
            viewModel.generateImageForUpload(graphicsLayer1)

            viewModel.uploadImage()
            viewModel.generateQrCode()

            isFirstState.value = false
            delay(200) // !!!!! TODO. timing issue
            viewModel.generateImage(graphicsLayer2)

            delay(1000)
            navigateToShare()
        }
    }
    GenerateImageContent(
        cutFrame = cutFrame,
        imageUri = imageUri,
        isFirstState = isFirstState.value,
        graphicsLayer1 = graphicsLayer1,
        graphicsLayer2 = graphicsLayer2
    )
}

// TODO: !!!!! left / right 20dp 마진 있었음
@Composable
fun GetDefaultFrame(
    cutFrame: DefaultCutFrameSet,
    imageUri: List<Uri>,
    graphicsLayer: GraphicsLayer,
    isForPrint: Boolean = false,
    isPaddingHorizontal: Dp = 0.dp,
    viewModel: GenerateImageViewModel = hiltViewModel()
) {
    Column (
        modifier = Modifier
            .drawWithContent {
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                drawLayer(graphicsLayer)
            }
    ) {
        if (isForPrint) {
            WhiteBox(isPaddingHorizontal)
            Row {
                WhiteBox(isPaddingHorizontal)
                DefaultCutFrame(cutFrame, imageUri, viewModel.qrImageBitmap)
                DefaultCutFrame(cutFrame, imageUri, viewModel.qrImageBitmap)
                WhiteBox(isPaddingHorizontal)
            }
            WhiteBox(isPaddingHorizontal)
        } else {
            Row {
                DefaultCutFrame(cutFrame, imageUri)
            }
        }
    }
}

@Composable
fun GenerateImageContent(
    cutFrame: DefaultCutFrameSet,
    imageUri: List<Uri>,
    isFirstState: Boolean,
    graphicsLayer1: GraphicsLayer,
    graphicsLayer2: GraphicsLayer,
    isForPrint: Boolean = true,

){
    BasicScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "사진을 생성중입니다...",
                alignment = Alignment.CenterHorizontally
            )
        },
        bottomBar = {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(AppTheme.size.button),
                color = AppTheme.colorScheme.top,
                strokeWidth = AppTheme.size.icon
            )
        },
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            if (isFirstState) {
                // TODO: 나중에 다른 CutFrameSet 어떻게 처리해야 할지?
                GetDefaultFrame(
                    cutFrame = cutFrame,
                    imageUri = imageUri,
                    graphicsLayer = graphicsLayer1,
                )
            } else {
                GetDefaultFrame(
                    cutFrame = cutFrame,
                    imageUri = imageUri,
                    graphicsLayer = graphicsLayer2,
                    isForPrint = isForPrint,
                    isPaddingHorizontal = 16.dp
                )
            }
        }
    }
}

@Composable
private fun WhiteBox(
    size: Dp
) {
    Box(
        modifier = Modifier
            .width(size)
            .height(size)
            .background(Color.Transparent)
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultFrame() {
    FourCutTogetherTheme {
        GenerateImageContent(
            cutFrame = DefaultCutFrameSet.FourCutLight,
            imageUri = listOf(Uri.EMPTY, Uri.EMPTY, Uri.EMPTY, Uri.EMPTY),
            isFirstState = true,
            graphicsLayer1 = rememberGraphicsLayer(),
            graphicsLayer2 = rememberGraphicsLayer()

        )
    }
}