package com.example.blogtour.utils

import com.example.airtable.data.DTO
import com.example.airtable.data.URLImage
import com.example.blogtour.domain.Image
import com.example.blogtour.domain.Post

fun converterFromDTOtoPost(dto: DTO): MutableList<Post> {
    val dtoSize = dto.records.size
    val postListFromDTO: MutableList<Post> = mutableListOf()
    for (i in 0 until dtoSize)
        postListFromDTO.add(
            Post(dto.records[i].id,
                dto.records[i].fields.nickName,
                dto.records[i].fields.text,
                dto.records[i].fields.likeCount,
                dto.records[i].fields.location,
                dto.records[i].fields.dateTour,
                dto.records[i].createdTime,
                converterDTOUrlToPost(dto.records[i].fields.urlImage))
        )
    return postListFromDTO
}

fun converterDTOUrlToPost(urlDtoImage: List<URLImage>?): MutableList<Image>? {
    val dtoUrlImage = urlDtoImage?.let { it.size }
    val postingImage: MutableList<Image> = mutableListOf()
    dtoUrlImage?.let {
        for (i in 0 until it) {
            postingImage.add(
                Image(urlDtoImage[i].url)
            )
        }
    }
    return postingImage
}
