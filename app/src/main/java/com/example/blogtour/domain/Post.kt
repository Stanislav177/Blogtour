package com.example.blogtour.domain

import java.sql.Date

data class Post(
    val id: Long, // id поста
    val nickName: String, //nick
    val urlImage: String, // адрес фото, пока одна
    val text: String, // текст
    val likeCount: Long, // количество лайков
    val location: String, // адрес
    val dateTour: String, // дата поездки
    val dateAddition: String, // дата добавления поста
)
