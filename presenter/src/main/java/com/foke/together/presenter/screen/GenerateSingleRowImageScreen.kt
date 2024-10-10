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
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.presenter.frame.FourCutFrame
import com.foke.together.presenter.frame.MakerFaireFrame
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.theme.mediumContrastLightColorScheme
import com.foke.together.presenter.viewmodel.GenerateSingleRowImageViewModel
import com.foke.together.util.AppLog
import kotlinx.coroutines.launch

@Composable
fun GenerateSingleRowImageScreen(
    navigateToTwoRow: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: GenerateSingleRowImageViewModel = hiltViewModel()
) {

    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (editFrame, description) = createRefs()
        Text(
            text = "이미지를 생성중입니다",
            modifier = Modifier.constrainAs(description) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(editFrame.top)
            },
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
        Row(
            modifier = Modifier
                .constrainAs(editFrame) {
                    top.linkTo(description.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .aspectRatio(0.3333f)
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                }
        ) {
            GetFrame(
                cutFrameType = viewModel.cutFrameType.ordinal,
                imageUri = viewModel.imageUri
            )
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        AppLog.d("GenerateSingleRowImageScreen", "LifecycleEventEffect: ON_RESUME", "${viewModel.imageUri}")
        coroutineScope.launch {
            viewModel.generateImage(graphicsLayer)
        }
        navigateToTwoRow()
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultFrame() {
    FourCutTogetherTheme {
        GenerateSingleRowImageScreen(
            navigateToTwoRow = {},
            popBackStack = {}
        )
    }
}