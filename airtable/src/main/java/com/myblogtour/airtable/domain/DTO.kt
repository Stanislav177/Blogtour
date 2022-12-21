package com.myblogtour.airtable.domain

import com.google.gson.annotations.Expose

data class DTO(
    val records: List<Record>,
)

data class Record(
    @Expose
    val id: String,
    @Expose
    val createdTime: String,
    @Expose
    val fields: Fields,
)

data class Fields(
    @Expose
    val dateTour: String,
    @Expose
    val nickName: String,
    @Expose
    val id: Long,
    @Expose
    val location: String,
    @Expose
    val likeCount: Long,
    @Expose
    val text: String,
    @Expose
    val dateAddition: String,
    @Expose
    val urlImage: List<URLImage>? = null,
)

data class URLImage(
    @Expose
    val url: String,
)