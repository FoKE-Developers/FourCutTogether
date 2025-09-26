package com.foke.together.presenter.screen

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foke.together.presenter.R
import com.foke.together.presenter.component.AppTopBar
import com.foke.together.presenter.component.BasicScaffold
import com.foke.together.presenter.screen.state.InternalCameraState
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.InternelCameraViewModel
import com.foke.together.util.AppLog

private val TAG = "InternalCameraScreen"

/*TODO(CameraScreen, InternalCameraScreen 합치기) */
@Composable
fun InternalCameraScreenRoot(
    navigateToGenerateImage: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: InternelCameraViewModel = hiltViewModel()
){
    val state = viewModel.state
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val captureCount by viewModel.captureCount.collectAsStateWithLifecycle()
    val progress by viewModel.progressState.collectAsStateWithLifecycle()
    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.setCaptureTimer(context) { navigateToGenerateImage() }
        AppLog.d(TAG, "LifecycleEventEffect. ON_START", "")
    }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        AppLog.d(TAG, "LifecycleEventEffect. ON_RESUME", "")
        viewModel.startCaptureTimer()
    }
    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        viewModel.stopCaptureTimer()
        AppLog.d(TAG, "LifecycleEventEffect. ON_STOP", "")
    }
    InternalCameraScreen(
        state = state,
        captureCount = captureCount,
        progress = progress,
        initialPreview = { context, previewView ->
            viewModel.initial(context, lifecycleOwner, previewView)
        },
        releasePreview = { context ->
            viewModel.release(context)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InternalCameraScreen(
    state : InternalCameraState,
    captureCount : Int,
    progress: Float,
    initialPreview : (Context, PreviewView) -> Unit,
    releasePreview : (Context) -> Unit,
){
    BasicScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "${captureCount}번째 촬영",
                alignment = Alignment.CenterHorizontally
            )
        },
        bottomBar = {
            BottomAppBar {  }
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                text = stringResource(id = R.string.camera_exclamation_text),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier.aspectRatio(1.5f).fillMaxHeight()
            ) {
                AndroidView(
                    modifier = Modifier.align(Alignment.Center).fillMaxSize(),
                    factory = { context ->
                        PreviewView(context).also { preview ->
                            initialPreview(context, preview)
                        }
                    },
                    onRelease = { previewView ->
                        releasePreview(previewView.context)
                    }

                )
            }
        }
    }
}

@Preview(
    device = Devices.TABLET,
    showBackground = true,
)
@Composable
fun InternalScreenPreview(){
    val state by remember { mutableStateOf(InternalCameraState()) }
    val captureCount by remember{ mutableStateOf(1) }
    val progress by remember{ mutableStateOf(1f)}
    FourCutTogetherTheme() {
        InternalCameraScreen(
            state = state,
            captureCount = captureCount,
            progress = progress,
            initialPreview = { context, previewView ->

            },
            releasePreview = { context ->

            }
        )
    }
}