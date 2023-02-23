package com.myblogtour.airtable.domain

import com.google.gson.JsonObject
import retrofit2.Callback

interface RepoAirTable {

    fun createUserProfile(createUserProfile: JsonObject, callback: Callback<RecordUserProfileDTO>)
    fun createPublication(createPost: JsonObject, callback: Callback<Record>)
    fun updateComplaintPublication(id: String, complaint: JsonObject, callback: Callback<Unit>)
    fun getPublication(callback: Callback<PublicationDTO>)
    fun getMyPublication(id: String, callback: Callback<PublicationDTO>)
    fun getUserProfile(uid: String, callback: Callback<UserProfileDTO>)
    fun updateUserProfileLikeCounter(id: String, update: JsonObject, callback: Callback<Unit>)
}