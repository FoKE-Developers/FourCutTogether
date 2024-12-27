package com.foke.together.domain.interactor.entity

import androidx.annotation.DrawableRes
import com.foke.together.domain.R

abstract class CutFrame(
    @DrawableRes val frameImageSrc: Int, // TODO: asset 에 추가 및 src 값을 넣어서 처리
    val additionalFrameImageSrc: List<String>,
    val photoPosition: List<PhotoPosition>
)

sealed class DefaultCutFrameSet (
    frameImageSrc: Int, // TODO: asset 에 추가 및 src 값을 넣어서 처리
    additionalFrameImageSrc: List<String>,
    photoPosition: List<PhotoPosition>
): CutFrame(frameImageSrc, additionalFrameImageSrc, photoPosition) {
    val title = "기본"
    val coverImageSrc = ""

    // TODO: add information of frames
    data object FourCutLight: DefaultCutFrameSet(R.drawable.fourcut_frame_medium_light, emptyList(), emptyList())
    data object FourCurDark: DefaultCutFrameSet(R.drawable.fourcut_frame_medium_dark, emptyList(), emptyList())
    data object MakerFaire: DefaultCutFrameSet(R.drawable.maker_faire_frame, emptyList(), emptyList())
    data object Wedding1: DefaultCutFrameSet(R.drawable.maker_faire_frame, emptyList(), emptyList())
    data object Wedding2: DefaultCutFrameSet(R.drawable.maker_faire_frame, emptyList(), emptyList())

    data object None: DefaultCutFrameSet(0, emptyList(), emptyList())
}