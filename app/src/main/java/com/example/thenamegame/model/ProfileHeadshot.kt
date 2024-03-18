package com.example.thenamegame.model

data class ProfileHeadshot(
    val alt: String,
    val height: Int,
    val id: String,
    val mimeType: String,
    val type: String,
    private var _url: String,
    val width: Int,
) {
    val url: String
        get() = _url.replace("https", "http")
}
