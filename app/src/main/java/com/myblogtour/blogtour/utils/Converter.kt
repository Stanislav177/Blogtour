package com.myblogtour.blogtour.utils

import com.myblogtour.airtable.domain.*
import com.myblogtour.blogtour.domain.ImageEntity
import com.myblogtour.blogtour.domain.PublicationEntity
import com.myblogtour.blogtour.domain.UserProfileEntity

fun converterFromDtoToPublicationEntity(publicationDTO: PublicationDTO): List<PublicationEntity> {
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
                converterIconUserDto(publicationDTO.records[i].fields.iconFromUserProfile),
                converterNickNameUserDto(publicationDTO.records[i].fields.nickNameFromUserProfile),
                publicationDTO.records[i].fields.nicknameFromUserprofileFromCounterLike,
                converterCounterLike(publicationDTO.records[i].fields.counterLikeFromCounterLike),
                publicationDTO.records[i].fields.date
            )
        )
    }
    return publicationList
}

private fun converterNickNameUserDto(nickNameFromUserProfile: List<String>): String {
    val nickNameFromUserProfileSize = nickNameFromUserProfile.size
    var nickNameUser = ""
    for (i in 0 until nickNameFromUserProfileSize) {
        nickNameUser = nickNameFromUserProfile[i]
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
                    userProfileDTO.records[i].fields.nickname,
                    userProfileDTO.records[i].fields.publication,
                    converterIconUserDto(userProfileDTO.records[i].fields.icon)
                )

            }
        }
    }
    return profileUser
}

fun converterFromRegisterUserAirtableToUserEntity(recordUserProfileDTO: RecordUserProfileDTO) =
    UserProfileEntity(recordUserProfileDTO.id,
        recordUserProfileDTO.fields.nickname,
        recordUserProfileDTO.fields.publication,
        converterIconUserDto(recordUserProfileDTO.fields.icon)
    )


//fun converterFromDTOtoPost(dto: DTO): MutableList<PublicationEntity> {
//
//    val dtoSize = dto.records.size
//    val postListFromDTO: MutableList<PublicationEntity> = mutableListOf()
//    for (i in 0 until dtoSize)
//        postListFromDTO.add(
//            PublicationEntity(dto.records[i].id,
//                dto.records[i].fields.nickName,
//                dto.records[i].fields.text,
//                dto.records[i].fields.likeCount,
//                dto.records[i].fields.location,
//                dto.records[i].createdTime,
//                converterDTOUrlToPost(dto.records[i].fields.urlImage))
//        )
//    return postListFromDTO
//}

// переделать на map

