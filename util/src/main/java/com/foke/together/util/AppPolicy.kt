package com.foke.together.util

object AppPolicy {
    const val isDebugMode = true

    const val DEFAULT_EXTERNAL_CAMERA_IP = "0.0.0.0"

    // network
    const val WEB_SERVER_URL = "http://4cuts.store/"
    const val WEB_CONNECT_TIMEOUT = 10L
    const val WEB_READ_TIMEOUT = 10L
    const val WEB_WRITE_TIMEOUT = 10L
    const val WEB_FILE_MAX_CONTENT_LENGTH = 20971520

    const val EXTERNAL_CAMERA_DEFAULT_SERVER_URL = "http://0.0.0.0"
    const val EXTERNAL_CAMERA_CONNECT_TIMEOUT = 10L
    const val EXTERNAL_CAMERA_READ_TIMEOUT = 10L
    const val EXTERNAL_CAMERA_WRITE_TIMEOUT = 10L

    const val CAPTURE_INTERVAL = 10000L
    const val CAPTURE_COUNT = 4
    const val COUNTDOWN_INTERVAL = 10L
}