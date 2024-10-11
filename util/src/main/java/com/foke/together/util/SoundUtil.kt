package com.foke.together.util

import android.content.Context
import android.media.SoundPool

object SoundUtil {
    private val soundPool = SoundPool.Builder().build()
    private var cameraSoundId = 0
    private val onLoadCompleteListener = SoundPool.OnLoadCompleteListener { soundPool, sampleId, status ->
        soundPool.play(cameraSoundId, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun getCameraSound(context: Context) {
        cameraSoundId = soundPool.load(context,R.raw.capture_sound, 1)
        soundPool.setOnLoadCompleteListener(onLoadCompleteListener)
    }
}