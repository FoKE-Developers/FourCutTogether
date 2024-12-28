package com.foke.together.presenter.screen

import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.presenter.frame.DefaultCutFrame
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.GenerateImageViewModel
import com.foke.together.util.AppLog
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
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (frame1, frame2, description) = createRefs()
        Text(
            text = "이미지를 생성중입니다",
            modifier = Modifier.constrainAs(description) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(frame1.top)
            },
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )


        // 공유용 1장
        Row(
            modifier = Modifier
                .constrainAs(frame1) {
                    top.linkTo(description.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(frame2.top)
                }
                .aspectRatio(0.3333f)
                .drawWithContent {
                    graphicsLayer1.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer1)
                }
        ) {
            GetFrame(
                cutFrame = viewModel.cutFrame,
                imageUri = viewModel.imageUri
            )
        }


        // 인쇄용 2장
        Row(
            modifier = Modifier
                .constrainAs(frame2) {
                    top.linkTo(frame1.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .aspectRatio(0.6666f)
                .drawWithContent {
                    graphicsLayer2.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer2)
                }
        ) {
            GetFrame(
                cutFrame = viewModel.cutFrame,
                imageUri = viewModel.imageUri,
            )
            GetFrame(
                cutFrame = viewModel.cutFrame,
                imageUri = viewModel.imageUri,
            )
        }


    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        AppLog.d("GenerateSingleRowImageScreen", "LifecycleEventEffect: ON_RESUME", "${viewModel.imageUri}")
        coroutineScope.launch {
            viewModel.generateImage1(graphicsLayer1)
            viewModel.generateImage2(graphicsLayer2)
            navigateToShare()
        }
    }
}


// TODO: !!!!! left / right 20dp 마진 있었음
@Composable
fun GetFrame(
    cutFrame : CutFrame,
    imageUri: List<Uri>,
) {
    DefaultCutFrame(cutFrame, imageUri)
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