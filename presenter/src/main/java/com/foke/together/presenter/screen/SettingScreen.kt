package com.foke.together.presenter.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.theme.FourCutTogetherTheme

@Composable
fun SettingScreen(
    popBackStack: () -> Unit
) {
    FourCutTogetherTheme {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (backKey, selectCamera, IPAdrress) = createRefs()

            // TODO: viewmodel code. need to move SettingViewModel
            val cameraTypeList = listOf("Phone Cam", "External Cam")
            var cameraType by remember { mutableStateOf(cameraTypeList.indexOf("IP Cam")) }
            var cameraIPAddress by rememberSaveable { mutableStateOf("192.168.X.X") }

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
                        onClick = { cameraType = index },
                        modifier = when (index) {
                            0 ->
                                Modifier
                                    .offset(0.dp, 0.dp)
                                    .zIndex(if (cameraType == index) 1f else 0f)
                            else ->
                                Modifier
                                    .offset((-1 * index).dp, 0.dp)
                                    .zIndex(if (cameraType == index) 1f else 0f)
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
                            1.dp, if (cameraType == index) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
                        ),
                        // 선택되었을때의 색깔
                        colors = if (cameraType == index) {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                            // 선택되지않았을때
                        } else {
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
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomGuideLine)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
                value = cameraIPAddress,
                onValueChange = {cameraIPAddress = it},
                label = { Text(text = "IP Address") },
            )
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