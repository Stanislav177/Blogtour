package com.myblogtour.airtable.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserProfileDTO(
    val records: List<RecordUserProfile>,
)

data class RecordUserProfile(
    val id: String,
    val createdTime: String,
    val fields: FieldsUserProfile,
)

data class FieldsUserProfile(
    val nickName: String,
    val publication: List<String>,
    val uid: String,
    val icon: List<Icon>,
)

data class Icon(
    val url: String,
)

@Parcelize
data class PublicationDTO(
    val records: List<RecordPublication>,
) : Parcelable

@Parcelize
data class RecordPublication(
    val id: String,
    val createdTime: String,
    val fields: FieldsPublication,
) : Parcelable

@Parcelize
data class FieldsPublication(
    val id: Long,
    val like: Long,
    val text: String,
    val location: String,
    val userprofile: List<String>,
    val image: List<ImagePublication>,
    @SerializedName("icon (from userprofile)")
    val iconFromUserProfile: List<ImageIconPublication>,
    @SerializedName("nickname (from userprofile)")
    val nickNameFromUserProfile: List<String>,
) : Parcelable

@Parcelize
data class ImageIconPublication(
    val url: String,
) : Parcelable

@Parcelize
data class ImagePublication(
    val url: String,
) : Parcelable

data class Record(
    val id: String,
    val createdTime: String,
    val fields: Fields,
)

data class Fields(
    val nickName: String,
    val id: Long,
    val location: String,
    val likeCount: Long,
    val text: String,
    val urlImage: List<URLImage>? = null,
)

data class URLImage(
    val url: String,
)