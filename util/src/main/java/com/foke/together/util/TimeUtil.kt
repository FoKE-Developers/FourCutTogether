package com.foke.together.util

import java.text.SimpleDateFormat
import java.util.Date

object TimeUtil {
    fun getCurrentDisplayTime(): String {
        val dateFormat = "yyyy.MM.dd"
        // TODO: LocalDate로 변경
        val date = Date(System.currentTimeMillis())
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        val simpleDate: String = simpleDateFormat.format(date)
        return simpleDate
    }

    fun getCurrentTimeSec(): String {
        val timeFormat = "yyyyMMddHHmmss"
        // TODO: LocalDate로 변경
        val time = Date(System.currentTimeMillis())
        val simpleTimeFormat = SimpleDateFormat(timeFormat)
        val simpleTime: String = simpleTimeFormat.format(time)
        return simpleTime
    }

    fun getCurrentTimeMillis(): String {
        val timeFormat = "yyyyMMddHHmmssSSS"
        // TODO: LocalDate로 변경
        val time = Date(System.currentTimeMillis())
        val simpleTimeFormat = SimpleDateFormat(timeFormat)
        val simpleTime: String = simpleTimeFormat.format(time)
        return simpleTime
    }
}