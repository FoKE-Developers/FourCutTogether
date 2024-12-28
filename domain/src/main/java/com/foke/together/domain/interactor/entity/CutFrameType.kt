package com.foke.together.domain.interactor.entity

import androidx.annotation.DrawableRes
import com.foke.together.domain.R

abstract class CutFrame(
    val index: Int,
    val frameTitle: String,
    val cutCount: Int,
    val width: Int,
    val height: Int,
    @DrawableRes val frameImageSrc: Int, // !!!!! TODO: asset 에 추가 및 src 값을 넣어서 처리
    val photoPosition: List<PhotoPosition>,
    val additionalFrameImageSrc: List<Int>,  // !!!!! TODO: asset 에 추가 및 src 값을 넣어서 처리
)

sealed class DefaultCutFrameSet (
    index: Int,
    frameTitle: String,
    cutCount: Int,
    width: Int,
    height: Int,
    frameImageSrc: Int, // !!!!! TODO: asset 에 추가 및 src 값을 넣어서 처리
    photoPosition: List<PhotoPosition>,
    additionalFrameImageSrc: List<Int>, // !!!!! TODO: asset 에 추가 및 src 값을 넣어서 처리
    var isDateString: Boolean = false,
    val dateStringHeight: Int = 0,
): CutFrame(index, frameTitle, cutCount, width, height, frameImageSrc, photoPosition, additionalFrameImageSrc) {
    val cutFrameSetTitle = "기본"
    val cutFrameCoverImageSrc = ""

    // TODO: add information of frames
    data object FourCutLight: DefaultCutFrameSet(
        7,
        "같이네컷 화이트",
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
        8,
        "같이네컷 다크",
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

//    data object MakerFaire: DefaultCutFrameSet(
//        3,
//        "Maker Faire Seoul 2024",
//        4, 190, 570,
//        R.drawable.maker_faire_frame,
//        listOf(
//            PhotoPosition(159, 106, 16, 36),
//            PhotoPosition(159, 106, 16, 147),
//            PhotoPosition(159, 106, 16, 258),
//            PhotoPosition(159, 106, 16, 369),
//        ),
//        emptyList()
//    )

    data object Bride1: DefaultCutFrameSet(
        1,
        "신부 1",
        4, 190, 570,
        R.drawable.bride1,
        listOf(
            PhotoPosition(172, 115, 9, 9),
            PhotoPosition(172, 115, 9, 133),
            PhotoPosition(172, 115, 9, 256),
            PhotoPosition(172, 115, 9, 383),
        ),
        emptyList(),
        dateStringHeight = 498
    )

    data object Bride2: DefaultCutFrameSet(
        2,
        "신부 2",
        4, 190, 570,
        R.drawable.bride2,
        listOf(
            PhotoPosition(172, 115, 9, 9),
            PhotoPosition(172, 115, 9, 133),
            PhotoPosition(172, 115, 9, 256),
            PhotoPosition(172, 115, 9, 383),
        ),
        emptyList(),
        dateStringHeight = 495
    )

    data object Groom1: DefaultCutFrameSet(
        3,
        "신랑 1",
        4, 190, 570,
        R.drawable.groom1,
        listOf(
            PhotoPosition(172, 115, 9, 9),
            PhotoPosition(172, 115, 9, 133),
            PhotoPosition(172, 115, 9, 256),
            PhotoPosition(172, 115, 9, 383),
        ),
        emptyList(),
        dateStringHeight = 495
    )

    data object Groom2: DefaultCutFrameSet(
        4,
        "신랑 2",
        4, 190, 570,
        R.drawable.groom2,
        listOf(
            PhotoPosition(172, 115, 9, 9),
            PhotoPosition(172, 115, 9, 133),
            PhotoPosition(172, 115, 9, 256),
            PhotoPosition(172, 115, 9, 383),
        ),
        emptyList(),
        dateStringHeight = 495
    )

    data object Wedding1: DefaultCutFrameSet(
        5,
        "웨딩 1",
        4, 190, 570,
        R.drawable.wedding,
        listOf(
            PhotoPosition(172, 115, 9, 9),
            PhotoPosition(172, 115, 9, 133),
            PhotoPosition(172, 115, 9, 256),
            PhotoPosition(172, 115, 9, 383),
        ),
        listOf(
            R.drawable.wedding_overlay1
        ),
        dateStringHeight = 495
    )

    data object Wedding2: DefaultCutFrameSet(
        6,
        "웨딩 2",
        4, 190, 570,
        R.drawable.wedding,
        listOf(
            PhotoPosition(172, 115, 9, 9),
            PhotoPosition(172, 115, 9, 133),
            PhotoPosition(172, 115, 9, 256),
            PhotoPosition(172, 115, 9, 383),
        ),
        listOf(
            R.drawable.wedding_overlay2
        ),
        dateStringHeight = 495
    )
}