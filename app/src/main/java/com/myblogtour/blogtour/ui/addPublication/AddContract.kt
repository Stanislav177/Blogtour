package com.myblogtour.blogtour.ui.addPublication

import android.net.Uri
import androidx.lifecycle.LiveData

interface AddContract {

    interface ViewModel {
        val publishPostLiveData: LiveData<Boolean>
        val loadUri: LiveData<Uri?>
        val progressLoad: LiveData<Int>
        val errorMessageImage: LiveData<String>
        val errorMessageText: LiveData<String>
        val errorMessageLocation: LiveData<String>
        val errorMessagePublicationAdd: LiveData<String>
        fun dataPublication(
                text: String,
                location: String,
                imageUri: Uri?
        )
    }
}