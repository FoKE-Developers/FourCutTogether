package com.foke.together.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GetCutFrameSourceTypeUseCase
import com.foke.together.domain.interactor.SetCutFrameSourceTypeUseCase
import com.foke.together.domain.interactor.entity.CutFrameSourceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectFrameViewModel @Inject constructor(
    getCutFrameSourceTypeUseCase: GetCutFrameSourceTypeUseCase,
    private val setCutFrameSourceTypeUseCase: SetCutFrameSourceTypeUseCase
): ViewModel() {
    val cutFrameSourceType = getCutFrameSourceTypeUseCase().shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 1
    )
    fun setCutFrameSourceType(index: Int){
        setCutFrameSourceType(CutFrameSourceType.entries[index])
    }
    fun setCutFrameSourceType(type: CutFrameSourceType){
        viewModelScope.launch {
            setCutFrameSourceTypeUseCase(type)
        }
    }
}