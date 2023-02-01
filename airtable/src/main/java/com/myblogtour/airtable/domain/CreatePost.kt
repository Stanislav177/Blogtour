package com.myblogtour.airtable.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Field(
    @SerializedName("fields")
    val fields: CreatePost,
) : Parcelable

@Parcelize
data class CreatePost(
    @SerializedName("id")
    val id: Int, // id поста
    @SerializedName("nick_name")
    val nickName: String, //nick
    @SerializedName("text")
    val text: String, // текст
    @SerializedName("like_count")
    val likeCount: Int, // количество лайков
    @SerializedName("location")
    val location: String, // адрес
    @SerializedName("url_image")
    val urlImage: List<Image>? = null,
) : Parcelable

@Parcelize
data class Image(
    @SerializedName("url")
    val url: String,
) : Parcelable
