package com.foke.together.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import com.foke.together.domain.interactor.entity.MakerFaireFrame
import com.foke.together.domain.interactor.entity.Status
import com.foke.together.domain.interactor.session.UpdateSessionStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectFrameViewModel @Inject constructor(
    private val generatePhotoFrameUseCase: GeneratePhotoFrameUseCase,
    private val updateSessionStatusUseCase: UpdateSessionStatusUseCase
): ViewModel() {

    fun updateSessionStatus() {
        updateSessionStatusUseCase(Status.SELECT_FRAME)
    }

    fun setCutFrameType(type:Int) = viewModelScope.launch {
        // TODO: remove deprecated code
        generatePhotoFrameUseCase.setCutFrameType(type)
        updateSessionStatusUseCase(MakerFaireFrame())
    }
}