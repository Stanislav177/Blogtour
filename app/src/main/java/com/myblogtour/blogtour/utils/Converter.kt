package com.myblogtour.blogtour.utils

import com.myblogtour.airtable.domain.*
import com.myblogtour.blogtour.domain.ImageEntity
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.domain.UserProfileEntity
import java.time.format.DateTimeFormatter

fun converterFromDtoToPublicationEntity(
    id: String,
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
                false
            )
        )
    }
    return publicationList
}

private fun converterDateFormat(datePublication: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val parserDate = formatter.parse(datePublication)
    val displayFormatter = DateTimeFormatter.ofPattern("HH:mm dd MMMM yyyy")
    return displayFormatter.format(parserDate)
}

private fun checkListForNull(nickNameLikeDto: List<String>?): String {
    nickNameLikeDto?.let {
        return it.joinToString()
    }
    return ""
}

private fun converterIdTableCounterLike(idCounterLike: List<String>): String {
    val idCounterTableLikeSize = idCounterLike.size
    var idTable = ""
    for (i in 0 until idCounterTableLikeSize) {
        idTable = idCounterLike[i]
    }
    return idTable
}

private fun searchUserLikePublication(id: String, userListLike: List<String>?): Boolean {
    userListLike?.let {
        val searchUser = userListLike.binarySearch(id)
        if (searchUser >= 0) {
            return true
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

private fun converterIconUserDto(iconUserProfileDTO: List<IconUser>): String {
    val iconFromUserProfileSize = iconUserProfileDTO.size
    var iconUser = ""
    for (i in 0 until iconFromUserProfileSize) {
        iconUser = iconUserProfileDTO[i].url
    }
    return iconUser
}

private fun converterCounterLike(counterLikeAirtable: List<Long>): Long {
    val counterLikeSize = counterLikeAirtable.size
    var counterLike = 0L
    for (i in 0 until counterLikeSize) {
        counterLike = counterLikeAirtable[i]
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

fun converterFromProfileUserDtoToProfileUserEntity(
    userProfileDTO: UserProfileDTO,
): UserProfileEntity? {
    val userProfileDToSize = userProfileDTO.records.size
    var profileUser: UserProfileEntity? = null
    if (userProfileDToSize != 0) {
        userProfileDToSize.let {
            for (i in 0 until it) {
                profileUser = UserProfileEntity(
                    userProfileDTO.records[i].id,
                    userProfileDTO.records[i].fields.uid,
                    userProfileDTO.records[i].fields.nickname,
                    userProfileDTO.records[i].fields.publication,
                    converterIconUserDto(userProfileDTO.records[i].fields.icon),
                    userProfileDTO.records[i].fields.likePublication
                )
            }
        }
    }
    return profileUser
}

fun converterFromRegisterUserAirtableToUserEntity(recordUserProfileDTO: RecordUserProfileDTO) =
    UserProfileEntity(
        recordUserProfileDTO.id,
        recordUserProfileDTO.fields.uid,
        recordUserProfileDTO.fields.nickname,
        recordUserProfileDTO.fields.publication,
        converterIconUserDto(recordUserProfileDTO.fields.icon),
        recordUserProfileDTO.fields.likePublication
    )

