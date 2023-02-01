package com.myblogtour.blogtour.domain

data class PublicationEntity(
    val id: String, // id поста
    val text: String, // текст
    val likeCount: Long, // количество лайков
    val location: String, // city
    val createdTime: String, // дата добавления поста
    val urlImage: List<ImageEntity>, // картинка
    val userProfile: String, // id
    val iconFromUserProfile: String, //icon
    val nickNameFromUserProfile: String, // nickname
)

data class ImageEntity(
    val url: String? = null, // url image

)
