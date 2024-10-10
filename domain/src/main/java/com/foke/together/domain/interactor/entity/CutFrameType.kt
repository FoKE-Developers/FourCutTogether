package com.foke.together.domain.interactor.entity

enum class CutFrameType {
    MAKER_FAIRE,
    FOURCUT_LIGHT,
    FOURCUT_DARK;

    companion object {
        fun findBy(name: String): CutFrameType {
            return when (name) {
                MAKER_FAIRE.name -> MAKER_FAIRE
                FOURCUT_LIGHT.name -> FOURCUT_LIGHT
                FOURCUT_DARK.name -> FOURCUT_DARK
                else -> throw IllegalArgumentException("Unknown value: $name")
            }
        }
        fun findBy(ordinal: Int): CutFrameType {
            return when (ordinal) {
                MAKER_FAIRE.ordinal -> MAKER_FAIRE
                FOURCUT_LIGHT.ordinal -> FOURCUT_LIGHT
                FOURCUT_DARK.ordinal -> FOURCUT_DARK
                else -> throw IllegalArgumentException("Unknown value: $ordinal")
            }
        }
    }
}