package com.myblogtour.blogtour.utils

import android.icu.text.SimpleDateFormat
import com.myblogtour.airtable.domain.IconUser
import com.myblogtour.airtable.domain.ImagePublication
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import com.myblogtour.blogtour.domain.ImageEntity
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.domain.UserProfileEntity
import java.time.format.DateTimeFormatter

fun converterFromDtoToPublicationEntity(
    currentUser: Boolean,
    id: String?,
    publicationDTO: PublicationDTO,
): List<PublicationEntity> {
    val publicationSize = publicationDTO.records.size
    val publicationList: MutableList<PublicationEntity> = mutableListOf()
    for (i in 0 until publicationSize) {
        publicationList.add(
            PublicationEntity(
                publicationDTO.records[i].id,
                publicationDTO.records[i].fields.text,
                publicationDTO.records[i].fields.location,
                converterUrlImageDto(publicationDTO.records[i].fields.image),
                converterUserProfileIdDto(publicationDTO.records[i].fields.userprofile),
                converterIconUserDto(publicationDTO.records[i].fields.iconprofile),
                converterNickNameUserDto(publicationDTO.records[i].fields.nicknamepublication),
                checkListForNull(publicationDTO.records[i].fields.nicknamelike),
                converterCounterLike(publicationDTO.records[i].fields.countlike),
                converterDateFormat(publicationDTO.records[i].fields.date),
                searchUserLikePublication(id, publicationDTO.records[i].fields.iduserprofile),
                converterIdTableCounterLike(publicationDTO.records[i].fields.idcounterlike),
                false,
                currentUser
            )
        )
    }
    return publicationList
}

private fun converterDateFormat(datePublication: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val date = sdf.parse(datePublication)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val parserDate = formatter.parse(sdf.format(date))
    val displayFormatter = DateTimeFormatter.ofPattern("HH:mm dd MMMM yyyy")
    return displayFormatter.format(parserDate)
}

private fun checkListForNull(nickNameLikeDto: List<String>?): String {
    nickNameLikeDto?.let {
        return it.joinToString()
    }
    return ""
}

private fun converterIdTableCounterLike(idCounterLike: List<String>?): String {
    var idTable = ""
    if (idCounterLike != null) {
        val idCounterTableLikeSize = idCounterLike.size
        for (i in 0 until idCounterTableLikeSize) {
            idTable = idCounterLike[i]
        }
    }
    return idTable
}

private fun searchUserLikePublication(id: String?, userListLike: List<String>?): Boolean {
    id?.let { idNotNull ->
        userListLike?.let {
            val searchUser = userListLike.sorted().binarySearch(idNotNull)
            if (searchUser >= 0) {
                return true
            }
        }
    }
    return false
}

private fun converterNickNameUserDto(nickNameFromUserProfileDto: List<String>?): String {
    var nickNameUser = " "
    nickNameFromUserProfileDto?.let { listDto ->
        val nickNameFromUserProfileSize = listDto.size
        for (i in 0 until nickNameFromUserProfileSize) {
            nickNameUser = nickNameFromUserProfileDto[i]
        }
    }
    return nickNameUser
}

private fun converterIconUserDto(iconUserProfileDTO: List<IconUser>?): String {
    var iconUser = ""
    iconUserProfileDTO?.let {
        val iconFromUserProfileSize = iconUserProfileDTO.size
        for (i in 0 until iconFromUserProfileSize) {
            iconUser = iconUserProfileDTO[i].url
        }
    }
    return iconUser
}

private fun converterCounterLike(counterLikeAirtable: List<Long>?): Long {
    var counterLike = 0L
    if (counterLikeAirtable != null) {
        val counterLikeSize = counterLikeAirtable!!.size
        for (i in 0 until counterLikeSize) {
            counterLike = counterLikeAirtable[i]
        }
    }
    return counterLike
}

private fun converterUserProfileIdDto(userProfile: List<String>): String {
    val userIdSize = userProfile.size
    var idUser = ""
    for (i in 0 until userIdSize) {
        idUser = userProfile[i]
    }
    return idUser
}

private fun converterUrlImageDto(urlDtoImage: List<ImagePublication>): MutableList<ImageEntity> {
    val urlDtoImageSize = urlDtoImage?.let { it.size }
    val publicationImage: MutableList<ImageEntity> = mutableListOf()
    urlDtoImageSize?.let {
        for (i in 0 until it) {
            publicationImage.add(
                ImageEntity(urlDtoImage[i].url)
            )
        }
    }
    return publicationImage
}


fun converterFromRegisterUserAirtableToUserEntity(
    recordUserProfileDTO: RecordUserProfileDTO
) = UserProfileEntity(
    recordUserProfileDTO.id,
    recordUserProfileDTO.fields.uid,
    recordUserProfileDTO.fields.nickname,
    recordUserProfileDTO.fields.publication,
    converterIconUserDto(recordUserProfileDTO.fields.icon),
    recordUserProfileDTO.fields.likePublication,
    false,
    "",
    recordUserProfileDTO.fields.location,
    recordUserProfileDTO.fields.datebirth,
    recordUserProfileDTO.fields.usergender
)

fun converterFromProfileUserAirtableToUserEntity(
    userVer: Boolean,
    userMail: String,
    recordUserProfileDTO: RecordUserProfileDTO
) = UserProfileEntity(
    recordUserProfileDTO.id,
    recordUserProfileDTO.fields.uid,
    recordUserProfileDTO.fields.nickname,
    recordUserProfileDTO.fields.publication,
    converterIconUserDto(recordUserProfileDTO.fields.icon),
    recordUserProfileDTO.fields.likePublication,
    userVer,
    userMail,
    recordUserProfileDTO.fields.location,
    recordUserProfileDTO.fields.datebirth,
    recordUserProfileDTO.fields.usergender
)

