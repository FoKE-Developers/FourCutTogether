package com.foke.together.domain.interactor.entity

enum class FramePosition {
    LEFT,
    RIGHT;
    fun findBy(ordinal: Int): FramePosition {
        return when (ordinal) {
            LEFT.ordinal -> LEFT
            RIGHT.ordinal -> RIGHT
            else -> throw IllegalArgumentException("Unknown value: $ordinal")
        }
    }
}