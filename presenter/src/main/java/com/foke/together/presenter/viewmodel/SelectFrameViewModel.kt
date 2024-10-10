package com.foke.together.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectFrameViewModel @Inject constructor(
    private val generatePhotoFrameUseCase: GeneratePhotoFrameUseCase
): ViewModel() {
    fun setCutFrameType(type:Int) = viewModelScope.launch {
         generatePhotoFrameUseCase.setCutFrameType(type)
    }
}