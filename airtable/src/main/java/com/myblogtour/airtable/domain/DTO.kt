package com.myblogtour.airtable.domain

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserProfileDTO (
    val records: List<RecordUserProfile>
)

data class RecordUserProfile (
    val id: String,
    val createdTime: String,
    val fields: FieldsUserProfile
)

data class FieldsUserProfile (
    val nickName: String,
    val publication: List<String>,
    val uid: String,
    val icon: List<Icon>
)

data class Icon (
    val url: String,
)
@Parcelize
data class PublicationDTO (
    val records: List<RecordPublication>
) : Parcelable

@Parcelize
data class RecordPublication (
    val id: String,
    val createdTime: String,
    val fields: Fields
) : Parcelable

@Parcelize
data class Fields (
    val id: Long,
    val like: Long,
    val text: String,
    val location: String,
    val userprofile: List<String>,
    val image: List<ImagePublication>,
    @SerializedName("icon (from userprofile)")
    val iconFromUserProfile: List<ImageIcon>,
    @SerializedName("nickname (from userprofile)")
    val nickNameFromUserProfile: List<String>
) : Parcelable

@Parcelize
data class ImageIcon (
    val url: String,
) : Parcelable

@Parcelize
data class ImagePublication (
    val url: String,
) : Parcelable

data class DTO(
    val records: List<Records>,
)

data class Records(
    @Expose
    val id: String,
    @Expose
    val createdTime: String,
    @Expose
    val fields: Fieldsss,
)

data class Fieldsss(
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
    val urlImage: List<URLImage>? = null,
)

data class URLImage(
    @Expose
    val url: String,
)