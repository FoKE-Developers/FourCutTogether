package com.foke.together.util

object AppPolicy {
    const val isDebugMode = false

    // network
    const val WEB_SERVER_URL = "https://4cuts.store/"
    const val WEB_CONNECT_TIMEOUT = 10L
    const val WEB_READ_TIMEOUT = 10L
    const val WEB_WRITE_TIMEOUT = 10L
    const val WEB_FILE_MAX_CONTENT_LENGTH = 20971520

    const val EXTERNAL_CAMERA_DEFAULT_SERVER_URL = "http://0.0.0.0"
    const val EXTERNAL_CAMERA_CONNECT_TIMEOUT = 10L
    const val EXTERNAL_CAMERA_READ_TIMEOUT = 10L
    const val EXTERNAL_CAMERA_WRITE_TIMEOUT = 10L

    // capture settings
    const val CAPTURE_INTERVAL = 10000L
    const val CAPTURE_COUNT = 4
    const val COUNTDOWN_INTERVAL = 10L

    // file settings
    const val CAPTURED_FOUR_CUT_IMAGE_NAME = "capture"
    const val SINGLE_ROW_FINAL_IMAGE_NAME = "final_single_row"
    const val TWO_ROW_FINAL_IMAGE_NAME = "final_two_row"
    const val DEFAULT_QR_CODE_IMAGE_NAME = "qrcode"
}