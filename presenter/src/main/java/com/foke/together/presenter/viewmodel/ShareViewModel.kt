package com.foke.together.presenter.viewmodel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GetCutFrameSourceTypeUseCase
import com.foke.together.domain.interactor.SetCutFrameSourceTypeUseCase
import com.foke.together.domain.interactor.entity.CutFrameSourceType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    getCutFrameSourceTypeUseCase: GetCutFrameSourceTypeUseCase,
    private val setCutFrameSourceTypeUseCase: SetCutFrameSourceTypeUseCase
): ViewModel() {
    val cutFrameSourceType = getCutFrameSourceTypeUseCase().shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 1
    )
}

@Composable
fun GenerateFrame(
    frameType: CutFrameSourceType
){
    ConstraintLayout(
        modifier = Modifier
            .aspectRatio(ratio = 0.6666f)
    ) {
        LazyRow(

        ){


        }
    }
}