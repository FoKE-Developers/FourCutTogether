package com.foke.together.presenter.viewmodel

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GetCaptureDurationUseCase
import com.foke.together.domain.interactor.SetCaptureDurationUseCase
import com.foke.together.domain.interactor.GetCameraSourceTypeUseCase
import com.foke.together.domain.interactor.GetExternalCameraIPUseCase
import com.foke.together.domain.interactor.GetInternalCameraLensFacingUseCase
import com.foke.together.domain.interactor.SetCameraSourceTypeUseCase
import com.foke.together.domain.interactor.SetExternalCameraIPUseCase
import com.foke.together.domain.interactor.SetInternalCameraLensFacingUseCase
import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.domain.interactor.entity.ExternalCameraIP
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import com.foke.together.util.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getCameraSourceTypeUseCase: GetCameraSourceTypeUseCase,
    private val setCameraSourceTypeUseCase: SetCameraSourceTypeUseCase,
    private val getExternalCameraIPUseCase: GetExternalCameraIPUseCase,
    private val setExternalCameraIPUseCase: SetExternalCameraIPUseCase,
    private val getInternalCameraLensFacingUseCase: GetInternalCameraLensFacingUseCase,
    private val setInternalCameraLensFacingUseCase: SetInternalCameraLensFacingUseCase,
    private val getCaptureDurationUseCase: GetCaptureDurationUseCase,
    private val setCaptureDurationUseCase: SetCaptureDurationUseCase,
): ViewModel() {
    val cameraSourceType = getCameraSourceTypeUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = CameraSourceType.INTERNAL
    )
    val cameraTypeIndex = getCameraSourceTypeUseCase().map { sourceType ->
        sourceType.ordinal
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = CameraSourceType.INTERNAL.ordinal
    )

    val cameraSelector = getInternalCameraLensFacingUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = CameraSelector.LENS_FACING_FRONT
    )

    val cameraIPAddress = getExternalCameraIPUseCase().map{ ipClass ->
        ipClass.address
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ""
    )

    val email = MutableStateFlow("4cuts@foke.io")
    val password = MutableStateFlow("password")

    val captureDuration = getCaptureDurationUseCase().map{ duration ->
        duration.toLong(DurationUnit.MILLISECONDS)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AppPolicy.CAPTURE_INTERVAL.toLong(DurationUnit.MILLISECONDS)
    )

    fun setCameraSourceType(index: Int) = viewModelScope.launch {
        AppLog.e(TAG, "setCameraSourceType", "type: $index")
        setCameraSourceTypeUseCase(CameraSourceType.entries[index])
    }

    fun setCameraIPAddress(address: String) = viewModelScope.launch {
        setExternalCameraIPUseCase(ExternalCameraIP(address))
    }

    fun setCameraSelector(index: Int) = viewModelScope.launch{
        setInternalCameraLensFacingUseCase(index)
    }

    fun setEmail(address: String){
        email.value = address
    }

    fun setPassword(address: String){
        password.value = address
    }

    fun setCaptureDuration(duration : Long) = viewModelScope.launch {
        setCaptureDurationUseCase(duration.toDuration(DurationUnit.MILLISECONDS))
    }

    companion object {
        private val TAG = SettingViewModel::class.java.simpleName
    }
}