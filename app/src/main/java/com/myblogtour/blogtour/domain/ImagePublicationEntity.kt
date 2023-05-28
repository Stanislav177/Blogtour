package com.myblogtour.blogtour.domain

import android.net.Uri

data class ImagePublicationEntity(
    var url: Uri? = null,
    var uriLocal: Uri? = null,
    var loading: Boolean = true,
    var progress: Int = 0
)