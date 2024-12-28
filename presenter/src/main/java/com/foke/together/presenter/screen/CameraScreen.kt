package com.foke.together.presenter.screen

import android.graphics.Bitmap
import android.graphics.Rect
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.longdo.mjpegviewer.MjpegView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.foke.together.presenter.R
import com.foke.together.presenter.viewmodel.CameraViewModel
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import com.longdo.mjpegviewer.MjpegViewError
import com.longdo.mjpegviewer.MjpegViewStateChangeListener

@Composable
fun CameraScreen(
    navigateToGenerateImage: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: CameraViewModel = hiltViewModel()
) {
    val TAG = "CameraScreen"
    var mjpegView: MjpegView? = null
    val externalCameraIP = viewModel.externalCameraIP
    var frameCount = 0
    val graphicsLayer = rememberGraphicsLayer()

    DisposableEffect(Unit) {
        viewModel.updateSessionStatus()
        onDispose { }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backKey, title, preview, cameraTimer, imageCount, progress) = createRefs()

        LinearProgressIndicator(
            // TODO: progress State를 Flow로 구현하기
            progress = { viewModel.progressState },
            modifier = Modifier
                .constrainAs(progress) {
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    bottom.linkTo(title.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
                .height(30.dp)
        )

        Box(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(preview.top)
                }
                .padding(top = 30.dp),
        ) {
            Text(
                text = stringResource(id = R.string.camera_exclamation_text),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }

        AndroidView(
            modifier = Modifier
                .constrainAs(preview) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    bottom.linkTo(imageCount.top)
                }
                .aspectRatio(1.5f)
                .drawWithContent {
                    // call record to capture the content in the graphics layer
                    graphicsLayer.record {
                        // draw the contents of the composable into the graphics layer
                        this@drawWithContent.drawContent()
                    }
                    // draw the graphics layer on the visible canvas
                    drawLayer(graphicsLayer)
                },
            factory = { context ->
                MjpegView(context).apply {
                    mode = MjpegView.MODE_BEST_FIT
                    isAdjustHeight = true
                    supportPinchZoomAndPan = false
                    stateChangeListener = object: MjpegViewStateChangeListener {
                        override fun onStreamDownloadStart() {
                            AppLog.d(TAG, "MjpegView", "onStreamDownloadStart")
                        }

                        override fun onStreamDownloadStop() {
                            AppLog.d(TAG, "MjpegView", "onStreamDownloadStop")
                        }

                        override fun onServerConnected() {
                            AppLog.d(TAG, "MjpegView", "onServerConnected")
                        }

                        override fun onMeasurementChanged(rect: Rect?) {
                            AppLog.d(TAG, "MjpegView", "onMeasurementChanged")
                        }

                        override fun onNewFrame(image: Bitmap?) {
                            // stream 한장 받을때마다 오는 콜백
                            //TODO: 화면 로딩(30프레임 이상) 후 재실행
                            frameCount++
                            if(frameCount > 10) {
                                frameCount = 0
                                viewModel.startCaptureTimer()
                            }
                        }

                        override fun onError(error: MjpegViewError?) {
                            AppLog.d(TAG, "MjpegView", "onError, ${error?.cause ?: "unknown error"}")
                        }
                    }
                    setUrl(externalCameraIP)
                }
            },
        ){
            mjpegView = it
        }

        Text(
            text = "${viewModel.captureCount} / ${AppPolicy.CAPTURE_COUNT}",
            modifier = Modifier
                .constrainAs(imageCount) {
                    top.linkTo(preview.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
    }
    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.setCaptureTimer(graphicsLayer) { navigateToGenerateImage() }
        AppLog.d(TAG, "LifecycleEventEffect. ON_START", mjpegView.toString())
    }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        AppLog.d(TAG, "LifecycleEventEffect. ON_RESUME", mjpegView.toString())
        mjpegView?.startStream()
    }
    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        viewModel.stopCaptureTimer()
        AppLog.d(TAG, "LifecycleEventEffect. ON_STOP", mjpegView.toString())
        frameCount = 0
        mjpegView?.stopStream()
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
                navigateToGenerateImage = {},
                popBackStack = {}
            )
        }
    }
}