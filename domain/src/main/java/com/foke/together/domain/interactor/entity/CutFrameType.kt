package com.foke.together.domain.interactor.entity

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    var copyrightPosition: TextStickerPosition,
    var datePosition: TextStickerPosition,
    var qrCodePosition: QrStickerPosition,
    var isDateString: Boolean = false,
    var isQrCode: Boolean = false
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
        emptyList(),
        datePosition = TextStickerPosition(80, 10, 91,541,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        copyrightPosition = TextStickerPosition(80, 10, 91,547,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        qrCodePosition = QrStickerPosition(30,16,530)
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
    emptyList(),
    datePosition = TextStickerPosition(80, 10, 91,541,R.font.cascadia_mono, color = Color(170, 170, 170), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
    copyrightPosition = TextStickerPosition(80, 10, 91,547,R.font.cascadia_mono, color = Color(170, 170, 170), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
    qrCodePosition = QrStickerPosition(30,16,530)
)

    data object MakerFaire: DefaultCutFrameSet(
        9,
        "Maker Faire Seoul 2024",
        4, 190, 570,
        R.drawable.maker_faire_frame,
        listOf(
            PhotoPosition(159, 106, 16, 36),
            PhotoPosition(159, 106, 16, 147),
            PhotoPosition(159, 106, 16, 258),
            PhotoPosition(159, 106, 16, 369),
        ),
        emptyList(),
        datePosition = TextStickerPosition(80, 10, 91,541,R.font.cascadia_mono, color = Color(238, 238, 238), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        copyrightPosition = TextStickerPosition(80, 10, 91,547,R.font.cascadia_mono, color = Color(238, 238, 238), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        qrCodePosition = QrStickerPosition(30,16,530)
    )

    data object MakerFaire25_1: DefaultCutFrameSet(
        9,
        "Maker Faire Seoul 2025 - 1",
        4, 190, 570,
        R.drawable.maker_faire_frame_25_1,
        listOf(
            PhotoPosition(171, 114, 9, 20),
            PhotoPosition(171, 114, 9, 148),
            PhotoPosition(171, 114, 9, 269),
            PhotoPosition(171, 114, 9, 389),
        ),
        listOf(
            R.drawable.maker_faire_frame_25_1
        ),
        datePosition = TextStickerPosition(80, 10, 92,546,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        copyrightPosition = TextStickerPosition(80, 10, 92,551,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        qrCodePosition = QrStickerPosition(30,145,515)
    )

    data object MakerFaire25_2: DefaultCutFrameSet(
        9,
        "Maker Faire Seoul 2025 - 1",
        4, 190, 570,
        R.drawable.maker_faire_frame_25_2,
        listOf(
            PhotoPosition(171, 114, 9, 20),
            PhotoPosition(171, 114, 9, 148),
            PhotoPosition(171, 114, 9, 269),
            PhotoPosition(171, 114, 9, 389),
        ),
        listOf(
            R.drawable.maker_faire_frame_25_2
        ),
        datePosition = TextStickerPosition(80, 10, 92,546,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        copyrightPosition = TextStickerPosition(80, 10, 92,551,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        qrCodePosition = QrStickerPosition(30,145,515)
    )

    data object MakerFaire25_3: DefaultCutFrameSet(
        9,
        "Maker Faire Seoul 2025 - 3",
        4, 190, 570,
        R.drawable.maker_faire_frame_25_3,
        listOf(
            PhotoPosition(171, 114, 9, 20),
            PhotoPosition(171, 114, 9, 148),
            PhotoPosition(171, 114, 9, 269),
            PhotoPosition(171, 114, 9, 389),
        ),
        listOf(
            R.drawable.maker_faire_frame_25_3
        ),
        datePosition = TextStickerPosition(80, 10, 92,546,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        copyrightPosition = TextStickerPosition(80, 10, 92,551,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        qrCodePosition = QrStickerPosition(30,145,515)
    )

    data object MakerFaire25_4: DefaultCutFrameSet(
        9,
        "Maker Faire Seoul 2025 - 4",
        4, 190, 570,
        R.drawable.maker_faire_frame_25_4,
        listOf(
            PhotoPosition(171, 114, 9, 20),
            PhotoPosition(171, 114, 9, 148),
            PhotoPosition(171, 114, 9, 269),
            PhotoPosition(171, 114, 9, 389),
        ),
        listOf(
            R.drawable.maker_faire_frame_25_4
        ),
        datePosition = TextStickerPosition(80, 10, 92,546,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        copyrightPosition = TextStickerPosition(80, 10, 92,551,R.font.cascadia_mono, color = Color(34, 34, 34), fontWeight = FontWeight.ExtraBold, fontSize = 6, textAlign = TextAlign.End),
        qrCodePosition = QrStickerPosition(30,145,515)
    )

    companion object {
        val entries = listOf(
            FourCutLight,
            FourCurDark,
            MakerFaire,
            MakerFaire25_1,
            MakerFaire25_2,
            MakerFaire25_3,
            MakerFaire25_4,
        ).sortedBy { it.index }
    }
}