package com.myblogtour.blogtour.ui.addPublication

import android.net.Uri

interface MyOnClickListenerPosition {
    fun onItemClick(uriLocal: Uri)
    fun onItemClickCancel()
}