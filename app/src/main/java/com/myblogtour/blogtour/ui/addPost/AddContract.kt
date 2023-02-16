package com.myblogtour.blogtour.ui.addPost

import android.net.Uri
import androidx.lifecycle.LiveData

interface AddContract {

    interface ViewModel {
        val publishPostLiveData: LiveData<Boolean>
        val loadUri: LiveData<Uri?>
        val progressLoad: LiveData<Int>
        fun dataPost(
            text: String,
            location: String,
            imageUri: Uri
        )
    }
}