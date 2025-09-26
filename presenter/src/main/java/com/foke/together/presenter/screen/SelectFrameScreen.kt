package com.foke.together.presenter.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.entity.DefaultCutFrameSet
import com.foke.together.presenter.component.AppBottomBar
import com.foke.together.presenter.component.AppTopBar
import com.foke.together.presenter.component.BasicScaffold
import com.foke.together.presenter.theme.AppTheme
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.SelectFrameViewModel

@Composable
fun SelectFrameScreen(
    navigateToMethod: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: SelectFrameViewModel = hiltViewModel()
) {
    val cutFrames by viewModel.cutFrames.collectAsState()
    val isDateDisplay by viewModel.isDateDisplay.collectAsState()
    val isQRDisplay by viewModel.isQRDisplay.collectAsState()

    DisposableEffect(Unit) {
        viewModel.updateSessionStatus()

        onDispose { }
    }
    SelectFrameContent(
        cutFrames = cutFrames,
        isDateDisplay = isDateDisplay,
        isQRDisplay = isQRDisplay,
        selectFrame = {
            viewModel.setCutFrameType(it)
            navigateToMethod()
        },
        onDisplayDate = { isDateDisplay ->
            viewModel.updateDateDisplay(isDateDisplay)
        },
        onDisplayQR ={ isQRDisplay ->
            viewModel.updateQRDisplay(isQRDisplay)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectFrameContent(
    cutFrames: List<DefaultCutFrameSet>,
    isDateDisplay: Boolean,
    isQRDisplay : Boolean,
    selectFrame: (cutFrame: CutFrame) -> Unit,
    onDisplayDate : (Boolean ) -> Unit,
    onDisplayQR : (Boolean) -> Unit,
){
    BasicScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "프레임을 선택하세요",
                alignment = Alignment.CenterHorizontally,
            )
        },
        bottomBar = {
            AppBottomBar {
                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Checkbox(
                        checked = isDateDisplay,
                        onCheckedChange = { isChecked ->
                            onDisplayDate(isChecked)
                        }
                    )
                    Text(
                        text = "날짜 표시하기",
                        style = AppTheme.typography.title
                    )
                }

                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Checkbox(
                        checked = isQRDisplay,
                        onCheckedChange = { isChecked ->
                            onDisplayQR(isChecked)
                        }
                    )
                    Text(
                        text = "QR 표시하기",
                        style = AppTheme.typography.title
                    )
                }
            }
        }
    ){ paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = AppTheme.size.layoutPadding,
                    end = AppTheme.size.layoutPadding
                ),
            userScrollEnabled = true,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.size.layoutPadding),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.layoutPadding),
            columns = GridCells.Fixed(6)
        ){
            items(cutFrames.size){ index ->
                Image(
                    modifier = Modifier.aspectRatio(0.3333f)
                        .clickable(
                            true,
                            onClick = {
                                selectFrame(cutFrames[index])
                            }
                        ),
                    painter = painterResource(id = cutFrames[index].frameImageSrc),
                    contentDescription = cutFrames[index].frameTitle
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET,
    uiMode = Configuration.ORIENTATION_LANDSCAPE
)
@Composable
private fun DefaultPreview() {
    FourCutTogetherTheme {
        val cutFrames by remember { mutableStateOf(DefaultCutFrameSet.entries) }
        var isDateDisplay by remember { mutableStateOf(false) }
        var isQRDisplay by remember { mutableStateOf(false) }
        SelectFrameContent(
            cutFrames = cutFrames,
            isDateDisplay = isDateDisplay,
            isQRDisplay = isQRDisplay,
            selectFrame = {},
            onDisplayDate = { isDateDisplay = it },
            onDisplayQR = { isQRDisplay  = it }
        )
    }
}