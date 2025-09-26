package com.foke.together.util

import androidx.compose.ui.unit.dp
import kotlin.time.Duration.Companion.seconds

object AppPolicy {
    const val isDebugMode = true // TODO: change to false
    const val isNoCameraDebugMode = false // TODO: change to false

    // network
    const val WEB_SERVER_URL = "https://4cut.us/"
    const val WEB_CONNECT_TIMEOUT = 20L
    const val WEB_READ_TIMEOUT = 20L
    const val WEB_WRITE_TIMEOUT = 20L
    const val WEB_FILE_MAX_CONTENT_LENGTH = 20971520

    const val EXTERNAL_CAMERA_DEFAULT_SERVER_URL = "http://0.0.0.0"
    const val EXTERNAL_CAMERA_CONNECT_TIMEOUT = 10L
    const val EXTERNAL_CAMERA_READ_TIMEOUT = 10L
    const val EXTERNAL_CAMERA_WRITE_TIMEOUT = 10L

    // capture settings
    val CAPTURE_INTERVAL = 7.seconds
    const val CAPTURE_COUNT = 4
    const val COUNTDOWN_INTERVAL = 10L
    const val PREVIEW_INTERVAL = 5L
    val PRINT_IMAGE_MARGIN = 15.dp

    // file settings
    const val CAPTURED_FOUR_CUT_IMAGE_NAME = "capture"
    const val SINGLE_ROW_FINAL_IMAGE_NAME = "final_single_row"
    const val TWO_ROW_FINAL_IMAGE_NAME = "final_two_row"
    const val DEFAULT_QR_CODE_IMAGE_NAME = "qrcode"

    const val MEDIA_STORE_RELATIVE_PATH = "Pictures/4cuts"
}