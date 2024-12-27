package com.foke.together.presenter.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.domain.interactor.entity.CutFrameTypeV1
import com.foke.together.presenter.R
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.SelectFrameViewModel

@Composable
fun SelectFrameScreen(
    navigateToMethod: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: SelectFrameViewModel = hiltViewModel()
) {
    DisposableEffect(Unit) {
        viewModel.updateSessionStatus()
        onDispose { }
    }

    FourCutTogetherTheme {
        val pagerState = rememberPagerState(
            initialPage = CutFrameTypeV1.MAKER_FAIRE.ordinal
        ) {
            CutFrameTypeV1.entries.size // 총 페이지 수 설정
        }
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (backKey, title, pager, frameSelectButton) = createRefs()
            val topGuideLine = createGuidelineFromTop(0.1f)
            val bottomGuideLine = createGuidelineFromBottom(0.1f)
            val startGuideLine = createGuidelineFromStart(0.1f)
            val endGuideLine = createGuidelineFromEnd(0.1f)

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
                    start = 40.dp,
                    end = 40.dp
                )
            ) { page ->

                // TODO: usecase를 통해서 모든 프레임 정보를 가져오도록 함

                when(page){
                    CutFrameTypeV1.MAKER_FAIRE.ordinal -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clickable {
                                    viewModel.setCutFrameType(pagerState.currentPage)
                                    navigateToMethod()
                                }
                        ) { Image(painter = painterResource(id = com.foke.together.domain.R.drawable.maker_faire_frame), contentDescription = "maker_faire_frame") }
                    }
                    CutFrameTypeV1.FOURCUT_LIGHT.ordinal -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clickable {
                                    viewModel.setCutFrameType(pagerState.currentPage)
                                    navigateToMethod()
                                }

                        ) { Image(painter = painterResource(id = com.foke.together.domain.R.drawable.fourcut_frame_medium_light), contentDescription = "fourcut_frame_medium_light") }
                    }
                    CutFrameTypeV1.FOURCUT_DARK.ordinal -> {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    viewModel.setCutFrameType(pagerState.currentPage)
                                    navigateToMethod()
                                },
                            contentAlignment = Alignment.Center
                        ) { Image(painter = painterResource(id = com.foke.together.domain.R.drawable.fourcut_frame_medium_dark), contentDescription = "fourcut_frame_medium_dark") }
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.setCutFrameType(pagerState.currentPage)
                    navigateToMethod()
                },
                modifier = Modifier.constrainAs(frameSelectButton) {
                    top.linkTo(pager.bottom)
                    start.linkTo(startGuideLine)
                    end.linkTo(endGuideLine)
                    bottom.linkTo(bottomGuideLine)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                }.width(200.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.select_frame_button_next),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
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