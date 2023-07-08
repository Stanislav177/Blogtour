package com.myblogtour.blogtour.domain.repository

import android.net.Uri

interface ImageFbRepository {

    fun imageLoading(
        uri: Uri,
        onSuccess: ((Uri)) -> Unit,
        onError: ((String)) -> Unit,
        onProgress: ((Int)) -> Unit,
    )

    fun deleteImage(uri: List<Uri?>)
    fun deleteImage(uri: Uri)
    fun cancelLoading()
}