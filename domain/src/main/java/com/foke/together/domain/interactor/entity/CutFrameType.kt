package com.foke.together.domain.interactor.entity

import androidx.annotation.DrawableRes
import com.foke.together.domain.R

sealed class CutFrame (
    @DrawableRes val frameImageSrc: Int, // TODO: asset 에 추가 및 src 값을 넣어서 처리
    val additionalFrameImageSrc: List<String>,
    val photoPosition: List<PhotoPosition>
)

// TODO: add information of frames
class FourCutLightFrame: CutFrame(R.drawable.fourcut_frame_medium_light, emptyList(), emptyList())
class FourCurDarkFrame: CutFrame(R.drawable.fourcut_frame_medium_dark, emptyList(), emptyList())
class MakerFaireFrame: CutFrame(R.drawable.maker_faire_frame, emptyList(), emptyList())
class WeddingFrame1: CutFrame(R.drawable.maker_faire_frame, emptyList(), emptyList())
class WeddingFrame2: CutFrame(R.drawable.maker_faire_frame, emptyList(), emptyList())

class NoneFrame: CutFrame(0, emptyList(), emptyList())
