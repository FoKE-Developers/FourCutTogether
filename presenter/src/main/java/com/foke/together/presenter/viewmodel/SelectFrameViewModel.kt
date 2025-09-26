package com.foke.together.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.entity.DefaultCutFrameSet
import com.foke.together.domain.interactor.entity.Status
import com.foke.together.domain.interactor.session.UpdateSessionStatusUseCase
import com.foke.together.util.AppLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectFrameViewModel @Inject constructor(
    private val updateSessionStatusUseCase: UpdateSessionStatusUseCase
): ViewModel() {

    val isDateDisplay = MutableStateFlow(false)
    val isQRDisplay = MutableStateFlow(false)

    val cutFrames = MutableStateFlow<List<DefaultCutFrameSet>>(emptyList())

    fun updateSessionStatus() {
        updateSessionStatusUseCase(Status.SELECT_FRAME)
        cutFrames.value = DefaultCutFrameSet.entries
    }

    fun updateDateDisplay(state: Boolean) = viewModelScope.launch{
        isDateDisplay.value = state
        cutFrames.value = DefaultCutFrameSet.entries.map { cutFrame ->
            cutFrame.isDateString = state
            AppLog.d(TAG, "updateDateDisplay", "isDateString : ${cutFrame.isDateString}")
            cutFrame
        }
    }

    fun updateQRDisplay( state: Boolean ) = viewModelScope.launch{
        isQRDisplay.emit(state)
        cutFrames.value = DefaultCutFrameSet.entries.map { cutFrame ->
            cutFrame.isDateString = state
            cutFrame
        }
    }

    fun setCutFrameType(cutFrame: CutFrame) = viewModelScope.launch {
        updateSessionStatusUseCase(cutFrame)
    }

    companion object {
        private val TAG = SelectFrameViewModel::class.java.simpleName
    }
}