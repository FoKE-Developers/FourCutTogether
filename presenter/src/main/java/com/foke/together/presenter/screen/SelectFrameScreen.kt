package com.foke.together.presenter.screen

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.domain.interactor.entity.DefaultCutFrameSet
import com.foke.together.presenter.R
import com.foke.together.presenter.frame.DefaultCutFrame
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.SelectFrameViewModel

@Composable
fun SelectFrameScreen(
    navigateToMethod: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: SelectFrameViewModel = hiltViewModel()
) {
    val cutFrames = remember { mutableStateOf<List<DefaultCutFrameSet>>(emptyList()) }
    val isDateDisplay = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        viewModel.updateSessionStatus()

        // get frames
        cutFrames.value = DefaultCutFrameSet::class.sealedSubclasses.mapNotNull { classes ->
            classes.objectInstance
        }.sortedBy { it.index }

        onDispose { }
    }

    FourCutTogetherTheme {
        val pagerState = rememberPagerState(
            initialPage = 0
        ) {
            cutFrames.value.size // 총 페이지 수 설정
        }
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (backKey, title, pager, isDateDisplayCheckBox, frameSelectButton) = createRefs()
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
                    .padding(top = 30.dp, bottom = 30.dp)
                    .constrainAs(pager) {
                        top.linkTo(title.bottom)
                        start.linkTo(startGuideLine)
                        end.linkTo(endGuideLine)
                        bottom.linkTo(isDateDisplayCheckBox.top)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                verticalAlignment = Alignment.Top,
                state = pagerState,
                pageSize = PageSize.Fixed(250.dp),
                contentPadding = PaddingValues(
                    start = 120.dp,
                    end = 120.dp,
                )
            ) { page ->
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clickable {
                                viewModel.setCutFrameType(cutFrames.value[page])
                                navigateToMethod()
                            }
                    ) {
                        DefaultCutFrame(
                            cutFrames.value[page],
                            listOf(
                                // !!!!! TODO: empty 혹은 다른 이미지로 교체
                                Uri.parse("file:///android_asset/sample_cut.png"),
                                Uri.parse("file:///android_asset/sample_cut.png"),
                                Uri.parse("file:///android_asset/sample_cut.png"),
                                Uri.parse("file:///android_asset/sample_cut.png"),
                            ),

                        )
                    }
                    Text(
                        text = cutFrames.value[page].frameTitle,
                        fontWeight = FontWeight.Bold,
                        color = Color(200,200,200),
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            Row (
                modifier = Modifier
                    .constrainAs(isDateDisplayCheckBox) {
                        top.linkTo(pager.bottom)
                        start.linkTo(startGuideLine)
                        end.linkTo(endGuideLine)
                        bottom.linkTo(frameSelectButton.top)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    }
                    .padding(bottom = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isDateDisplay.value,
                    onCheckedChange = { isChecked ->
                        isDateDisplay.value = isChecked
                        cutFrames.value = cutFrames.value.map {
                            it.isDateString = isChecked
                            it
                        }
                    }
                )
                Text(
                    text = "날짜 표시하기",
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Button(
                onClick = {
                     viewModel.setCutFrameType(cutFrames.value[pagerState.currentPage])
                    navigateToMethod()
                },
                modifier = Modifier
                    .constrainAs(frameSelectButton) {
                        top.linkTo(isDateDisplayCheckBox.bottom)
                        start.linkTo(startGuideLine)
                        end.linkTo(endGuideLine)
                        bottom.linkTo(bottomGuideLine)
                        height = Dimension.wrapContent
                        width = Dimension.wrapContent
                    }
                    .width(200.dp),
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
    FourCutTogetherTheme {
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