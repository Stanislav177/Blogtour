package com.myblogtour.blogtour.domain

data class UserProfileEntity(
    val id: String,
    val nickname: String,
    val publication: List<String>?,
    val icon: String,
    val likePublication: List<String>?
)
