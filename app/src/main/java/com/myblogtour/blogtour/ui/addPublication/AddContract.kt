package com.myblogtour.blogtour.ui.addPublication

import android.net.Uri
import android.text.Editable
import androidx.lifecycle.LiveData
import com.myblogtour.blogtour.domain.ImagePublicationEntity

interface AddContract {

    interface ViewModel {
        val publishPostLiveData: LiveData<Boolean>
        val loadUriImage: LiveData<ImagePublicationEntity>
        val progressLoad: LiveData<ImagePublicationEntity>
        val errorMessageImage: LiveData<String>
        val errorMessageText: LiveData<String>
        val errorMessageLocation: LiveData<String>
        val errorMessagePublicationAdd: LiveData<String>
        val address: LiveData<Editable>
        val errorAddress: LiveData<String>
        val counterImage: LiveData<Boolean>
        val loadingImage: LiveData<Boolean>
        val amountImage: LiveData<Int>
        val loadingImageFb: LiveData<Uri>
        fun dataPublication(
            text: String,
            location: String,
        )
        fun getAddress(lat: Double?, lon: Double?)
    }
}