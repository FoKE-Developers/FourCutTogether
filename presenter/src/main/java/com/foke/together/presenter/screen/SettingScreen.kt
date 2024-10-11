package com.foke.together.presenter.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.theme.FourCutTogetherTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.presenter.viewmodel.SettingViewModel
import com.foke.together.util.AppPolicy
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
    val cameraTypeList = CameraSourceType.entries.map { it.name }

    FourCutTogetherTheme {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (backKey, content) = createRefs()

            val topGuideLine = createGuidelineFromTop(0.1f)
            val bottomGuideLine = createGuidelineFromBottom(0.1f)
            val startGuideLine = createGuidelineFromStart(0.2f)
            val endGuideLine = createGuidelineFromEnd(0.2f)

            IconButton(
                onClick = { popBackStack() },
                modifier = Modifier.constrainAs(backKey) {
                    top.linkTo(topGuideLine)
                    start.linkTo(startGuideLine)
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
            Column(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(backKey.bottom)
                    start.linkTo(startGuideLine)
                    end.linkTo(endGuideLine)
                }
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Camera Source Selection
                Row(
                    modifier = Modifier.width(300.dp) // Make the row full width
                ) {
                    val cornerRadius = 16.dp
                    cameraTypeList.forEachIndexed { index, item ->
                        OutlinedButton(
                            onClick = {
                                viewModel.setCameraSourceType(index)
                            },
                            modifier = Modifier.weight(1f),
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

                                else -> RoundedCornerShape(0.dp)
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

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    modifier = Modifier.width(300.dp), // Make the TextField full width
                    value = viewModel.cameraIPAddressState,
                    onValueChange = {
                        viewModel.cameraIPAddressState = it
                        viewModel.setCameraIPAddress(it)
                    },
                    label = { Text(text = "Camera Server URL") },
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp).width(300.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    thickness = 1.dp
                )

                Column(modifier = Modifier.width(300.dp)) {
                    Text(text = "Account Info", style = MaterialTheme.typography.bodySmall)
                    Text(text = "user", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.width(300.dp)) {
                    Text(text = "Bearer Token", style = MaterialTheme.typography.bodySmall)
                    Text(text = "YourBearerTokenValueHere", style = MaterialTheme.typography.bodyLarge)
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp).width(300.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    thickness = 1.dp
                )

                TextField(
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "Email") },
                    enabled = false
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    modifier = Modifier.width(300.dp),
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "Password") },
                    enabled = false
                )

                TextButton(
                    onClick = { /* Handle login action */ },
                    modifier = Modifier
                        .padding(vertical = 16.dp) // Add some padding
                        .width(300.dp)
                        .height(48.dp), // Optional: Set a fixed height for the button
                    shape = RoundedCornerShape(16.dp),  // Slightly rounded corners
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,  // Button background color
                        contentColor = MaterialTheme.colorScheme.onPrimary   // Text color
                    ),
                    enabled = false
                ) {
                    Text(text = "Log In")
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp).width(300.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    thickness = 1.dp
                )

                TextField(
                    modifier = Modifier.width(300.dp),
                    value = AppPolicy.CAPTURE_INTERVAL.toString(),
                    onValueChange = { },
                    label = { Text(text = "Capture Interval") },
                    enabled = false
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