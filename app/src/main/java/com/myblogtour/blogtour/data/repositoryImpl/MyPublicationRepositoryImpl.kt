package com.myblogtour.blogtour.data.repositoryImpl

import com.myblogtour.airtable.BuildConfig
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.MyPublicationRepository
import io.reactivex.rxjava3.kotlin.subscribeBy

class MyPublicationRepositoryImpl(private val api: AirTableApi) : MyPublicationRepository {
    override fun getMyPublication(
        uidUserProfile: String,
        onSuccess: (PublicationDTO) -> Unit,
        onError: ((Throwable)) -> Unit,
    ) {
        api.getMyPublication(BuildConfig.API_KEY, uidUserProfile).subscribeBy(
            onSuccess = {
                onSuccess.invoke(it)
            },
            onError = {
                onError.invoke(it)
            }
        )
    }

    override fun deletePublication(
        idPublication: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        api.deletePublication(idPublication, BuildConfig.API_KEY).subscribeBy(
            onComplete = {
                onSuccess.invoke(true)
            },
            onError = {
                onError.invoke(it)
            }
        )
    }

    override fun deleteLikePublication(idLike: String) {
        api.deleteLike(idLike, BuildConfig.API_KEY).subscribe()
    }
}