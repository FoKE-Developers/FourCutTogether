package com.foke.together.presenter.screen

import androidx.camera.core.CameraSelector
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraFront
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.theme.FourCutTogetherTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.presenter.component.AppTextField
import com.foke.together.presenter.component.AppTopBar
import com.foke.together.presenter.component.BasicScaffold
import com.foke.together.presenter.component.MultiOptionsButton
import com.foke.together.presenter.component.SliderPreference
import com.foke.together.presenter.theme.AppTheme
import com.foke.together.presenter.viewmodel.SettingViewModel
import com.foke.together.util.AppPolicy
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

private const val CameraSourceTypeError = -1

@Composable
fun SettingScreen(
    popBackStack: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val cameraTypeIndex by viewModel.cameraTypeIndex.collectAsState()
    val cameraTypeList = CameraSourceType.entries.map { it.name }
    val cameraSelector by viewModel.cameraSelector.collectAsState()
    val cameraIPAddress by viewModel.cameraIPAddress.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val captureDuration by viewModel.captureDuration.collectAsState()


    SettingContent(
        popBackStack = popBackStack,
        cameraTypeList = cameraTypeList,
        cameraTypeIndex = cameraTypeIndex,
        onChangeCameraSourceType = { viewModel.setCameraSourceType(it) },

        cameraSelectorIndex = cameraSelector,
        onChangeCameraSelector = { viewModel.setCameraSelector(it) },

        ipAddress = cameraIPAddress,
        onIPAddressChange = { viewModel.setCameraIPAddress(it) },

        email = email,
        onEmailChange = {viewModel.setEmail(it)},
        password = password,
        onPasswordChange =  { viewModel.setPassword(it) },
        captureDuration = captureDuration,
        onCaptureDurationChange = {viewModel.setCaptureDuration(it)}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingContent(
    popBackStack: () -> Unit,

    cameraTypeList : List<String>,
    cameraTypeIndex: Int,
    onChangeCameraSourceType : (Int) -> Unit,

    cameraSelectorIndex: Int,
    onChangeCameraSelector : (Int) -> Unit,

    ipAddress : String,
    onIPAddressChange : (String) -> Unit,
    email : String,
    password : String,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,

    captureDuration : Long,
    onCaptureDurationChange : (Long) -> Unit,

){
    BasicScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "",
                leftIcon = {
                    Icon(
                        modifier = Modifier.size(AppTheme.size.icon)
                            .clickable(true, onClick = popBackStack),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "backKey",
                        tint = AppTheme.colorScheme.top
                    )
                }
            )
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = AppTheme.size.layoutPadding,
                    end = AppTheme.size.layoutPadding
                ),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            MultiOptionsButton(
                modifier = Modifier.wrapContentSize(),
                optionList = cameraTypeList,
                selectedOptionIndex = cameraTypeIndex,
                onChangeOption = onChangeCameraSourceType
            )

            SliderPreference(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                title = "Capture Duration",
                iconVector = Icons.Filled.CameraFront,
                sliderText = "${captureDuration / 1000}s",
                sliderValue = captureDuration.toFloat(),
                sliderRange = 1f..10f,
                onSliderValueChange = { sliderValue ->
                    onCaptureDurationChange(sliderValue.toLong())
                }
            )

            if(cameraTypeIndex == CameraSourceType.INTERNAL.ordinal){
                InternalSettingContent(
                    cameraSelectorIndex = cameraSelectorIndex,
                    onChangeCameraSelector = onChangeCameraSelector,
                )
            }
            if(cameraTypeIndex == CameraSourceType.EXTERNAL.ordinal){
                ExternalSettingContent(
                    ipAddress = ipAddress,
                    onIPAddressChange = onIPAddressChange,
                    email = email,
                    onEmailChange = onEmailChange,
                    password = password,
                    onPasswordChange = onPasswordChange,
                )
            }
        }
    }
}

@Composable
fun ExternalSettingContent(
    ipAddress : String,
    onIPAddressChange : (String) -> Unit,
    email : String,
    password : String,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,
){
    AppTextField(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(AppTheme.size.buttonPadding),
        value = ipAddress,
        onValueChange = onIPAddressChange,
        label = "Camera Server URL",
    )

    Text(text = "Account Info", style = AppTheme.typography.label)
    Text(text = "user", style = AppTheme.typography.label)

    Text(text = "Bearer Token", style = AppTheme.typography.label)
    Text(text = "YourBearerTokenValueHere", style = AppTheme.typography.label)


    AppTextField(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(AppTheme.size.buttonPadding),
        value = email,
        onValueChange = onEmailChange,
        label = "Email",
        enabled = false
    )

    AppTextField(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(AppTheme.size.buttonPadding),
        value = password,
        onValueChange = onPasswordChange,
        label = "Password",
        enabled = false
    )

    TextButton(
        onClick = { /* Handle login action */ },
        modifier = Modifier.fillMaxWidth().height(AppTheme.size.button),
        shape = AppTheme.shapes.button,  // Slightly rounded corners
        colors = ButtonDefaults.textButtonColors(
            containerColor = AppTheme.colorScheme.top,  // Button background color
            contentColor = AppTheme.colorScheme.tint   // Text color
        ),
        enabled = false
    ) {
        Text(text = "Log In")
    }
}

@Composable
fun InternalSettingContent(
    cameraSelectorIndex: Int,
    onChangeCameraSelector : (Int) -> Unit,
){
    MultiOptionsButton(
        modifier = Modifier.wrapContentSize(),
        optionList = listOf("Front", "Back"),
        selectedOptionIndex = cameraSelectorIndex,
        onChangeOption = onChangeCameraSelector
    )
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
private fun SettingContentPreview() {
    var cameraTypeList by remember { mutableStateOf(listOf("Internal", "External"))}
    var cameraTypeIndex by remember { mutableIntStateOf(CameraSourceType.EXTERNAL.ordinal ) }
    var cameraSelectorIndex by remember { mutableIntStateOf(CameraSelector.LENS_FACING_FRONT) }
    var ipAddress by remember { mutableStateOf("192.168.0.1")}
    var email by remember { mutableStateOf("4cuts@foke.io") }
    var password by remember { mutableStateOf("password") }
    var captureDuration by remember { mutableLongStateOf(5.seconds.toLong(DurationUnit.MILLISECONDS)) }
    FourCutTogetherTheme {
        SettingContent(
            popBackStack = {},
            cameraTypeList = cameraTypeList,
            cameraTypeIndex = cameraTypeIndex,
            onChangeCameraSourceType = { cameraTypeIndex = it },
            cameraSelectorIndex = cameraSelectorIndex,
            onChangeCameraSelector = { cameraSelectorIndex = it },
            ipAddress = ipAddress,
            onIPAddressChange = { ipAddress = it },
            email = email,
            onEmailChange = {email = it},
            password = password,
            onPasswordChange = {password = it},
            captureDuration = captureDuration,
            onCaptureDurationChange = {captureDuration = it}
        )
    }
}