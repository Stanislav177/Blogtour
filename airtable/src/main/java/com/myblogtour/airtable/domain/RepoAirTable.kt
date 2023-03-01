package com.myblogtour.airtable.domain

import com.google.gson.JsonObject
import retrofit2.Callback

interface RepoAirTable {

    fun createUserProfile(createUserProfile: JsonObject, callback: Callback<RecordUserProfileDTO>)// Готово
    fun createPublication(createPost: JsonObject, callback: Callback<Record>) // Готово
    fun updateComplaintPublication(id: String, complaint: JsonObject, callback: Callback<Unit>) // Готово
    fun getPublication(callback: Callback<PublicationDTO>) // Готово
    fun getMyPublication(id: String, callback: Callback<PublicationDTO>)
    fun getUserProfile(uid: String, callback: Callback<UserProfileDTO>)// Готово
    fun updateUserProfileLikeCounter(id: String, update: JsonObject, callback: Callback<Unit>)
    fun deletePublication(idPublication: String, callback: Callback<Unit>) // Готово
    fun deletePublicationLike(idLike: String, callback: Callback<Unit>)
}