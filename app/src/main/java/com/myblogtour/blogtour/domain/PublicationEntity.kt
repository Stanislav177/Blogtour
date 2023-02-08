package com.myblogtour.blogtour.domain

data class PublicationEntity(
    val id: String, // id поста
    val text: String, // текст
    val location: String, // city
    val urlImage: List<ImageEntity>, // картинка
    val userProfile: String, // id
    val iconFromUserProfile: String, //icon
    val nickNameFromUserProfile: String, // nickname
    val nicknameFromUserprofileFromCounterLike: List<String>,
    val counterLikeFromCounterLike: Long,
    val date: String,
)

data class ImageEntity(
    val url: String? = null, // url image

)
