package com.foke.together.presenter.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.R
import com.foke.together.presenter.ui.theme.FourCutTogetherTheme

@Composable
fun SelectFrameScreen(
    navigateToMethod: () -> Unit,
    popBackStack: () -> Unit
) {
    FourCutTogetherTheme {
        val pagerState = rememberPagerState {
            3 // 총 페이지 수 설정
        }
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (backKey, title, pager, frameSelectButton) = createRefs()
            val topGuideLine = createGuidelineFromTop(0.1f)
            val bottomGuideLine = createGuidelineFromBottom(0.1f)
            val startGuideLine = createGuidelineFromStart(0.2f)
            val endGuideLine = createGuidelineFromEnd(0.2f)

            IconButton(
                onClick = { popBackStack() },
                modifier = Modifier.constrainAs(backKey) {
                    top.linkTo(topGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(title.start)
                    bottom.linkTo(pager.top)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                },
            ) {
                //
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "backKey",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = "프레임을 선택하세요",
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(topGuideLine)
                    start.linkTo(startGuideLine)
                    end.linkTo(endGuideLine)
                    bottom.linkTo(pager.top)
                },
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            HorizontalPager(
                modifier = Modifier
                    .constrainAs(pager) {
                        top.linkTo(title.bottom)
                        start.linkTo(startGuideLine)
                        end.linkTo(endGuideLine)
                        bottom.linkTo(frameSelectButton.top)
                        width = Dimension.wrapContent
                        height = Dimension.fillToConstraints
                    }
                    .aspectRatio(0.5f),
                verticalAlignment = Alignment.Top,
                state = pagerState,
                pageSize = PageSize.Fill,
                contentPadding = PaddingValues(
                    start = 25.dp,
                    end = 25.dp
                )
            ) { page ->
                when(page){
                    0 -> Image(painter = painterResource(id = R.drawable.fourcut_frame_medium_light), contentDescription = "fourcut_frame_medium_light")
                    1 -> Image(painter = painterResource(id = R.drawable.fourcut_frame_medium_dark), contentDescription = "fourcut_frame_medium_dark")
                    2 -> Image(painter = painterResource(id = R.drawable.maker_paire_frame), contentDescription = "maker_faire_frame")


                }
            }
            IconButton(
                onClick = { navigateToMethod() },
                modifier = Modifier.constrainAs(frameSelectButton) {
                    top.linkTo(pager.bottom)
                    start.linkTo(startGuideLine)
                    end.linkTo(endGuideLine)
                    bottom.linkTo(bottomGuideLine)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                },
            ) {
                //
                Icon(
                    modifier = Modifier.size(85.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Camera Navigation Button Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
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
            SelectFrameScreen(
                navigateToMethod = {},
                popBackStack = {}
            )
        }
    }
}