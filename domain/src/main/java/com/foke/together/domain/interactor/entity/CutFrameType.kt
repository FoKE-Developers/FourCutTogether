package com.foke.together.domain.interactor.entity

import androidx.annotation.DrawableRes
import com.foke.together.domain.R

abstract class CutFrame(
    val index: Int,
    val frameTitle: String,
    val cutCount: Int,
    val width: Int,
    val height: Int,
    @DrawableRes val frameImageSrc: Int, // TODO: asset 에 추가 및 src 값을 넣어서 처리
    val photoPosition: List<PhotoPosition>,
    val additionalFrameImageSrc: List<String>,
)

sealed class DefaultCutFrameSet (
    index: Int,
    frameTitle: String,
    cutCount: Int,
    width: Int,
    height: Int,
    frameImageSrc: Int, // TODO: asset 에 추가 및 src 값을 넣어서 처리
    photoPosition: List<PhotoPosition>,
    additionalFrameImageSrc: List<String>
): CutFrame(index, frameTitle, cutCount, width, height, frameImageSrc, photoPosition, additionalFrameImageSrc) {
    val cutFrameSetTitle = "기본"
    val cutFrameCoverImageSrc = ""

    // TODO: add information of frames
    data object FourCutLight: DefaultCutFrameSet(
        1,
        "FourCut Light",
        4, 190, 570,
        R.drawable.fourcut_frame_medium_light,
        listOf(
            PhotoPosition(159, 106, 16, 36),
            PhotoPosition(159, 106, 16, 147),
            PhotoPosition(159, 106, 16, 258),
            PhotoPosition(159, 106, 16, 369),
        ),
        emptyList()
    )

    data object FourCurDark: DefaultCutFrameSet(
        2,
        "FourCut Dark",
        4, 190, 570,
        R.drawable.fourcut_frame_medium_dark,
        listOf(
            PhotoPosition(159, 106, 16, 36),
            PhotoPosition(159, 106, 16, 147),
            PhotoPosition(159, 106, 16, 258),
            PhotoPosition(159, 106, 16, 369),
        ),
        emptyList()
    )

    data object MakerFaire: DefaultCutFrameSet(
        3,
        "Maker Faire Seoul 2024",
        4, 190, 570,
        R.drawable.maker_faire_frame,
        listOf(
            PhotoPosition(159, 106, 16, 36),
            PhotoPosition(159, 106, 16, 147),
            PhotoPosition(159, 106, 16, 258),
            PhotoPosition(159, 106, 16, 369),
        ),
        emptyList()
    )

    data object Wedding1: DefaultCutFrameSet(
        4,
        "Wedding 1",
        4, 190, 570,
        R.drawable.maker_faire_frame,
        emptyList(),
        emptyList()
    )

    data object Wedding2: DefaultCutFrameSet(
        5,
        "Wedding 2",
        4, 190, 570,
        R.drawable.maker_faire_frame,
        emptyList(),
        emptyList()
    )
}