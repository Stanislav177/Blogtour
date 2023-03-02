package com.myblogtour.blogtour.domain

data class PublicationEntity(
    val id: String, // id поста
    val text: String, // текст
    val location: String, // city
    val urlImage: List<ImageEntity>, // картинка
    val userProfile: String, // id пользователя
    val iconFromUserProfile: String, //icon
    val nickNameUserProfile: String, // nickname
    val nickNameUserLike: String,
    var counterLike: Long, // количество лайков
    val date: String, // дата публикации
    var clickLikePublication: Boolean, // стоит лайк или нет
    val idCounterLike: String, // id таблицы с лайками
    var maxLinesText: Boolean,
    val currentUser: Boolean
)

data class ImageEntity(
    val url: String? = null, // url image

)
