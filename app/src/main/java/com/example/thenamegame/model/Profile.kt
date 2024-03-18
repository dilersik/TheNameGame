package com.example.thenamegame.model

data class Profile(
    val firstName: String,
    val headshot: ProfileHeadshot,
    val id: String,
    val jobTitle: String,
    val lastName: String,
    val slug: String,
    val socialLinks: List<SocialLink>,
    val type: String,
)
