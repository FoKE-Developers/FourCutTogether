package com.foke.together.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.entity.DefaultCutFrameSet
import com.foke.together.domain.interactor.entity.Status
import com.foke.together.domain.interactor.session.UpdateSessionStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectFrameViewModel @Inject constructor(
    private val updateSessionStatusUseCase: UpdateSessionStatusUseCase
): ViewModel() {

    val cutFrames = MutableStateFlow(DefaultCutFrameSet.entries)
    val isDateDisplay = MutableStateFlow(false)
    val isQRDisplay = MutableStateFlow(false)
    fun updateSessionStatus() {
        updateSessionStatusUseCase(Status.SELECT_FRAME)
    }

    fun updateDateDisplay(state: Boolean) = viewModelScope.launch{
        isDateDisplay.value = state
        cutFrames.value = cutFrames.value.map {
            it.isDateString = isDateDisplay.value
            it
        }
    }

    // TODO(QR 표시 기능 구현)
    fun updateQRDisplay( state: Boolean ) = viewModelScope.launch{
        isQRDisplay.value = state
        cutFrames.value = cutFrames.value.map {
            it
//            it.isQRString = isQRDisplay.value
//            it
        }
    }

    fun setCutFrameType(cutFrame: CutFrame) = viewModelScope.launch {
        updateSessionStatusUseCase(cutFrame)
    }
}