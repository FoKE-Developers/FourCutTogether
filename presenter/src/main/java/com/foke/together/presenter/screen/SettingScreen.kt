package com.foke.together.presenter.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.FactCheck
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.theme.FourCutTogetherTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.presenter.viewmodel.SettingViewModel
import kotlinx.coroutines.flow.map

private const val CameraSourceTypeError = -1

@Composable
fun SettingScreen(
    popBackStack: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val cameraSelectedIndex by remember {
        viewModel.cameraSourceType.map { CameraSourceType.entries.indexOf(it) }
    }.collectAsState(CameraSourceTypeError)
    val viewModelCameraIPAddress by remember {
        viewModel.cameraIPAddress.map { it.address }
    }.collectAsState("0.0.0.0")
    var cameraIPAddress: String = ""
    val cameraTypeList = CameraSourceType.entries.map { it.name }

    FourCutTogetherTheme {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (backKey, selectCamera, IPAdrress, IPButton) = createRefs()

            val topGuideLine = createGuidelineFromTop(0.1f)
            val bottomGuideLine = createGuidelineFromBottom(0.1f)
            val startGuideLine = createGuidelineFromStart(0.2f)
            val endGuideLine = createGuidelineFromEnd(0.2f)

            IconButton(
                onClick = { popBackStack() },
                modifier = Modifier.constrainAs(backKey) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(selectCamera.start)
                    bottom.linkTo(selectCamera.top)
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

            Row(
                modifier = Modifier.constrainAs(selectCamera){
                    top.linkTo(backKey.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(backKey.bottom)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
            ) {
                val cornerRadius = 16.dp
                cameraTypeList.forEachIndexed { index, item ->
                    OutlinedButton(
                        onClick = {
                            viewModel.setCameraSourceType(index)
                        },
                        modifier = when (index) {
                            0 ->
                                Modifier
                                    .offset(0.dp, 0.dp)
                                    .zIndex(1f)
                            else ->
                                Modifier
                                    .offset((-1 * index).dp, 0.dp)
                                    .zIndex(0f)
                        },
                        shape = when (index) {
                            0 -> RoundedCornerShape(
                                topStart = cornerRadius,
                                topEnd = 0.dp,
                                bottomStart = cornerRadius,
                                bottomEnd = 0.dp
                            )
                            cameraTypeList.size - 1 -> RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = cornerRadius,
                                bottomStart = 0.dp,
                                bottomEnd = cornerRadius
                            )
                            else -> RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        },
                        border = BorderStroke(
                            1.dp, if (cameraSelectedIndex == index) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
                        ),
                        colors = if (cameraSelectedIndex == index) {   // selected
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        } else {   // not selected
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        }
                    ) {
                        Text(item)
                    }
                }
            }

            TextField(
                modifier = Modifier.constrainAs(IPAdrress){
                    top.linkTo(selectCamera.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(IPButton.start)
                    bottom.linkTo(bottomGuideLine)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
                value = viewModelCameraIPAddress,
                onValueChange = { cameraIPAddress = it },
                label = { Text(text = "IP Address") },
            )
            IconButton(
                modifier = Modifier.constrainAs(IPButton){
                    top.linkTo(IPAdrress.top)
                    start.linkTo(IPAdrress.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(IPAdrress.bottom)
                    width = Dimension.wrapContent
                    height = Dimension.fillToConstraints
                },
                onClick = { viewModel.setCameraIPAddress(cameraIPAddress) }
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "IPButton",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun PhoneCamSetting(){
    // TODO: implement in phase 3
}

@Composable
fun ExternalCamSetting( modifier: Modifier) {
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    SettingScreen(
        popBackStack = {}
    )
}