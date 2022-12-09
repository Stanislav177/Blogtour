package com.example.airtable.data

data class DTO(
    val records: List<Record>,
)

data class Record(
    val id: String,
    val createdTime: String,
    val fields: Fields,
)

data class Fields(
    val dateTour: String,
    val nickName: String,
    val id: Long,
    val location: String,
    val likeCount: Long,
    val text: String,
    val dateAddition: String,
    val urlImage: List<URLImage>? = null,
)

data class URLImage(
    val url: String,
)