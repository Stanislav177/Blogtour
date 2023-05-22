package com.myblogtour.blogtour.ui.addPublication

import android.net.Uri
import android.text.Editable
import androidx.lifecycle.LiveData

interface AddContract {

    interface ViewModel {
        val publishPostLiveData: LiveData<Boolean>
        val loadUriOneImage: LiveData<Uri?>
        val loadUriTwoImage: LiveData<Uri?>
        val progressLoad: LiveData<Int>
        val errorMessageImage: LiveData<String>
        val errorMessageText: LiveData<String>
        val errorMessageLocation: LiveData<String>
        val errorMessagePublicationAdd: LiveData<String>
        val address: LiveData<Editable>
        val errorAddress: LiveData<String>
        fun dataPublication(
            text: String,
            location: String,
            imageUri: Uri?,
        )
        fun getAddress(lat: Double?, lon: Double?)
    }
}