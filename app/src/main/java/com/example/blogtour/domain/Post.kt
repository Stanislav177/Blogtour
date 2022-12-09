package com.example.blogtour.domain

data class Post(
    val id: String, // id поста
    val nickName: String, //nick
    val text: String, // текст
    val likeCount: Long, // количество лайков
    val location: String, // адрес
    val dateTour: String, // дата поездки
    val dateAddition: String, // дата добавления поста
    val urlImage: List<Image>? = null,
)

data class Image(
    val url: String,
)
