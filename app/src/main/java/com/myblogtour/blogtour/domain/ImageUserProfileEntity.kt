package com.myblogtour.blogtour.domain

import android.net.Uri

data class ImageUserProfileEntity(
    var url: Uri? = null,
    var uriLocal: Uri? = null,
    var progress: Int = 0
)
