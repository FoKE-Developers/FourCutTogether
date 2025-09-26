package com.foke.together.presenter.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.presenter.R
import com.foke.together.presenter.component.AppButton
import com.foke.together.presenter.component.AppTopBar
import com.foke.together.presenter.component.BasicScaffold
import com.foke.together.presenter.theme.AppTheme
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navigationSelectFrame: () -> Unit,
    navigateToSetting: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    DisposableEffect(Unit) {
        viewModel.createSession()
        onDispose { }
    }

    HomeScreenContent(
        navigationSelectFrame = navigationSelectFrame,
        navigateToSetting = navigateToSetting,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    navigationSelectFrame: () -> Unit,
    navigateToSetting: () -> Unit,
){
    BasicScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "",
                alignment = Alignment.CenterHorizontally,
                rightIcon = {
                    IconButton(
                        onClick = { navigateToSetting() },
                        modifier = Modifier.wrapContentSize(),
                    ) {
                        Icon(
                            modifier = Modifier.size(AppTheme.size.icon),
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Setting",
                            tint = AppTheme.colorScheme.top
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = AppTheme.size.layoutPadding,
                    end = AppTheme.size.layoutPadding
                ),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(
                    id = R.drawable.fourcut_together
                ),
                contentDescription = "FourCuts Together",
                modifier = Modifier.wrapContentSize(),
            )
            AppButton(
                onClick = { navigationSelectFrame() },
                modifier = Modifier.wrapContentSize()
                    .padding(AppTheme.size.buttonPadding)
            ) {
                Text(
                    text = stringResource(id = R.string.home_button_start),
                    style = AppTheme.typography.title,
                    color = AppTheme.colorScheme.tint
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PHONE
)
@Composable
private fun HomeScreenPortraitPreview() {
    FourCutTogetherTheme() {
        HomeScreenContent(
            navigationSelectFrame = {},
            navigateToSetting = {},
        )
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
private fun HomeScreenLandScapePreview() {
    FourCutTogetherTheme() {
        HomeScreenContent(
            navigationSelectFrame = {},
            navigateToSetting = {},
        )
    }
}