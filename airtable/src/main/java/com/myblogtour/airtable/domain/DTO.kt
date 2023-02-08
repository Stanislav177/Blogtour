package com.myblogtour.airtable.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
    val text: String,
    val location: String,
    val userprofile: List<String>,
    val image: List<ImagePublication>,
    @SerializedName("icon (from userprofile)")
    val iconFromUserProfile: List<IconUser>,
    @SerializedName("nickname (from userprofile)")
    val nickNameFromUserProfile: List<String>,

    @SerializedName("nickname (from userprofile) (from CounterLike)")
    val nicknameFromUserprofileFromCounterLike: List<String>,
    @SerializedName("countlike (from CounterLike)")
    val counterLikeFromCounterLike: List<Long>,
    val date: String

) : Parcelable

@Parcelize
data class IconUser(
    val url: String,
) : Parcelable

@Parcelize
data class ImagePublication(
    val url: String,
) : Parcelable

data class UserProfileDTO (
    val records: List<RecordUserProfileDTO>
)

data class RecordUserProfileDTO (
    val id: String,
    val fields: FieldsUserProfileDTO
)

data class FieldsUserProfileDTO (
    val nickname: String,
    val uid: String,
    val icon: List<IconUser>,
    val publication: List<String>
)

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