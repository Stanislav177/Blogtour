package com.myblogtour.blogtour.ui.myPublication

import androidx.lifecycle.LiveData
import com.myblogtour.blogtour.domain.PublicationEntity

interface MyPublicationContract {

    interface MyPublication {
        val listPublication: LiveData<List<PublicationEntity>>
    }
}