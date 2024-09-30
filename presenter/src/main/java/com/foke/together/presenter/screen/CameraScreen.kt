package com.foke.together.presenter.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.ui.theme.FourCutTogetherTheme
import com.longdo.mjpegviewer.MjpegView

@Composable
fun CameraScreen(
    navigateToShare: () -> Unit,
    popBackStack: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backKey, title, preview, cameraTimer, imageCount, progress) = createRefs()
        IconButton(
            onClick = { popBackStack() },
            modifier = Modifier.constrainAs(backKey) {
                top.linkTo(title.top)
                bottom.linkTo(title.bottom)
                start.linkTo(parent.start, margin = 24.dp)
                height = Dimension.wrapContent
                width = Dimension.wrapContent
            },
        ) {
            //
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "backKey",
                tint = MaterialTheme.colorScheme.primary
            )
        }
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
            modifier = Modifier.constrainAs(preview) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start, margin = 24.dp)
                end.linkTo(parent.end, margin = 24.dp)
                bottom.linkTo(imageCount.top)
            }
                .aspectRatio(1.5f),
            factory = { context ->
                MjpegView(context).apply {
                    mode = MjpegView.MODE_BEST_FIT
                    isAdjustHeight = true
                    supportPinchZoomAndPan = false
                    // test url
                    // TODO : change url in viewmodel
                    setUrl("https://192.168.137.100:8080/test.mjpg")
                    startStream()
                }
            },
        )
        Text(
            text = "1 / 4",
            modifier = Modifier.constrainAs(imageCount) {
                top.linkTo(preview.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
                // 네비게이션 테스트 코드
                .clickable { navigateToShare() },
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
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
                navigateToShare = { },
                popBackStack = { }
            )
        }
    }
}