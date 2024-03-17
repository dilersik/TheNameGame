package com.example.thenamegame.nav

enum class ProfileNavEnum {
    HOME,
    DETAIL;

    companion object {
        const val PARAM_NAME_MODE = "mode"
        const val MODE_PRACTICE = "practice"
        const val MODE_TIMED = "timed"

        fun fromRoute(route: String?): ProfileNavEnum =
            when (route?.substringBefore("/")) {
                HOME.name -> HOME
                DETAIL.name -> DETAIL
                else -> HOME
            }
    }
}