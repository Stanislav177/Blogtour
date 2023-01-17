package com.myblogtour.blogtour.ui.addPost

import androidx.lifecycle.LiveData
import com.google.gson.JsonObject

interface AddContract {

    interface ViewModel {
        val publishPostLiveData: LiveData<Boolean>
        fun dataPost(
            text: String,
            location: String,
        )
    }
}