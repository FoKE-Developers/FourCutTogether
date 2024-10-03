package com.foke.together.domain.interactor.entity

enum class CameraSourceType {
    INTERNAL,
    EXTERNAL;

    companion object {
        fun findBy(name: String): CameraSourceType {
            return when (name) {
                INTERNAL.name -> INTERNAL
                EXTERNAL.name -> EXTERNAL
                else -> throw IllegalArgumentException("Unknown value: $name")
            }
        }
    }
}