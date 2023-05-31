package com.myblogtour.blogtour.data.retrofit

import com.google.gson.JsonObject
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.airtable.domain.RecordUserProfileDTO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.*

const val URL_API_END_POINT_USER_PROFILE = "v0/appQW6UhhbjRHa0vs/UserProfile"
const val URL_API_END_POINT_PUBLICATION = "v0/appQW6UhhbjRHa0vs/Publication"
const val URL_API_END_POINT_LIKE = "v0/appQW6UhhbjRHa0vs/CounterLike/"
const val API_KEY = "api_key"

interface AirTableApi {

    @Headers("Content-Type: application/json")
    @POST("$URL_API_END_POINT_PUBLICATION/?")
    fun createPublication(
        @Query(API_KEY) apikey: String,
        @Body publication: JsonObject,
    ): Completable

    @GET("$URL_API_END_POINT_PUBLICATION/?sort[0][field]=date&sort[0][direction]=desc")
    fun getPublication(
        @Query(API_KEY) apikey: String,
    ): Single<PublicationDTO>

    @Headers("Content-Type: application/json")
    @PATCH("$URL_API_END_POINT_PUBLICATION/{id}")
    fun updatePublicationComplaint(
        @Path("id") idPublication: String,
        @Query(API_KEY) apikey: String,
        @Body complaint: JsonObject,
    ): Completable

    @Headers("Content-Type: application/json")
    @POST("$URL_API_END_POINT_USER_PROFILE/?")
    fun createUserProfile(
        @Query(API_KEY) apikey: String,
        @Body createUser: JsonObject,
    ): Single<RecordUserProfileDTO>

    @GET("$URL_API_END_POINT_USER_PROFILE/{id}")
    fun getProfileUser(
        @Path("id") idUser: String,
        @Query(API_KEY) apikey: String,
    ): Single<RecordUserProfileDTO>

    @Headers("Content-Type: application/json")
    @PATCH("$URL_API_END_POINT_USER_PROFILE/{id}/")
    fun updateUserProfileCounterLike(
        @Path("id") idUser: String,
        @Query(API_KEY) apikey: String,
        @Body post: JsonObject,
    ): Completable

    @Headers("Content-Type: application/json")
    @PATCH("$URL_API_END_POINT_USER_PROFILE/{id}/")
    fun updateUserProfile(
        @Path("id") idUser: String,
        @Query(API_KEY) apikey: String,
        @Body post: JsonObject,
    ): Single<RecordUserProfileDTO>

    @GET("$URL_API_END_POINT_PUBLICATION/?")
    fun getMyPublication(
        @Query(API_KEY) apikey: String,
        @Query("filterByFormula") searchMyPublication: String,
    ): Single<PublicationDTO>

    @GET("$URL_API_END_POINT_PUBLICATION/?")
    fun getSearchPublication(
        @Query(API_KEY) apikey: String,
        @Query("filterByFormula") search: String,
    ): Single<PublicationDTO>

    @DELETE("$URL_API_END_POINT_PUBLICATION/{id}/")
    fun deletePublication(
        @Path("id") idPublication: String,
        @Query(API_KEY) apikey: String,
    ): Completable

    @DELETE("$URL_API_END_POINT_LIKE{id}")
    fun deleteLike(
        @Path("id") idLike: String,
        @Query(API_KEY) apikey: String,
    ): Completable
}