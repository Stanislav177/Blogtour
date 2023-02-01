package com.myblogtour.blogtour.utils

import com.myblogtour.airtable.domain.ImageIcon
import com.myblogtour.airtable.domain.ImagePublication
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.blogtour.domain.ImageEntity
import com.myblogtour.blogtour.domain.PublicationEntity

fun converterFromDtoToPublicationEntity(publicationDTO: PublicationDTO): List<PublicationEntity> {
    val publicationSize = publicationDTO.records.size
    val publicationList: MutableList<PublicationEntity> = mutableListOf()
    for (i in 0 until publicationSize) {
        publicationList.add(
            PublicationEntity(
                publicationDTO.records[i].id,
                publicationDTO.records[i].fields.text,
                publicationDTO.records[i].fields.like,
                publicationDTO.records[i].fields.location,
                publicationDTO.records[i].createdTime,
                converterUrlImageDto(publicationDTO.records[i].fields.image),
                converterUserProfileIdDto(publicationDTO.records[i].fields.userprofile),
                converterIconUserDto(publicationDTO.records[i].fields.iconFromUserProfile),
                converterNickNameUserDto(publicationDTO.records[i].fields.nickNameFromUserProfile),
            )
        )
    }
    return publicationList
}

fun converterNickNameUserDto(nickNameFromUserProfile: List<String>): String {
    val nickNameFromUserProfileSize = nickNameFromUserProfile.size
    var nickNameUser = ""
    for (i in 0 until nickNameFromUserProfileSize) {
        nickNameUser = nickNameFromUserProfile[i]
    }
    return nickNameUser
}

private fun converterIconUserDto(iconFromUserProfile: List<ImageIcon>): String {
    val iconFromUserProfileSize = iconFromUserProfile.size
    var iconUser = ""
    for (i in 0 until iconFromUserProfileSize) {
        iconUser = iconFromUserProfile[i].url
    }
    return iconUser
}

private fun converterUserProfileIdDto(userProfile: List<String>): String {
    val userIdSize = userProfile.size
    var idUser: String = ""
    for (i in 0 until userIdSize) {
        idUser = userProfile[i]
    }
    return idUser
}

fun converterUrlImageDto(urlDtoImage: List<ImagePublication>): MutableList<ImageEntity> {
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

