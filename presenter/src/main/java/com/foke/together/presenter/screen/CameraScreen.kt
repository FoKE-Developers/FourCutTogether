package com.foke.together.presenter.screen

import android.graphics.Bitmap
import android.graphics.Rect
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
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
import com.foke.together.presenter.viewmodel.CameraViewModel
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import com.longdo.mjpegviewer.MjpegViewError
import com.longdo.mjpegviewer.MjpegViewStateChangeListener
import kotlinx.coroutines.flow.map

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
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backKey, title, preview, cameraTimer, imageCount, progress) = createRefs()

        LinearProgressIndicator(
            // TODO: progress State를 Flow로 구현하기
            progress = { viewModel.progressState },
            modifier = Modifier.constrainAs(progress){
                start.linkTo(parent.start, margin = 24.dp)
                end.linkTo(parent.end, margin = 24.dp)
                bottom.linkTo(title.top)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            },
        )
        Text(
            text = "촬영시 움직이지마세요",
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(preview.top)
            },
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )

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
                            AppLog.d(TAG, "onStreamDownloadStart")
                        }

                        override fun onStreamDownloadStop() {
                            AppLog.d(TAG, "onStreamDownloadStop")
                        }

                        override fun onServerConnected() {
                            AppLog.d(TAG, "onServerConnected")
                        }

                        override fun onMeasurementChanged(rect: Rect?) {
                            AppLog.d(TAG, "onMeasurementChanged")
                        }

                        override fun onNewFrame(image: Bitmap?) {
                            // stream 한장 받을때마다 오는 콜백
                            //TODO: 화면 로딩(30프레임 이상) 후 재실행
                            frameCount++
                            if(frameCount > 30) {
                                frameCount = 0
                                viewModel.startCaptureTimer()
                            }
                        }

                        override fun onError(error: MjpegViewError?) {
                            AppLog.d(TAG, "onError, ${error.toString()}")
                        }
                    }
                    // test url
                    // TODO : change url in viewmodel
                    setUrl("${externalCameraIP}")
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
        AppLog.d(TAG, "ON_START", mjpegView.toString())
    }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        AppLog.d(TAG, "ON_RESUME", mjpegView.toString())
        mjpegView?.startStream()
    }
    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        viewModel.stopCaptureTimer()
        AppLog.d(TAG, "ON_STOP", mjpegView.toString())
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