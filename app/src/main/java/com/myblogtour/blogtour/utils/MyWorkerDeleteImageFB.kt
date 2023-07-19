package com.myblogtour.blogtour.utils

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.myblogtour.blogtour.domain.repository.ImageFbRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MyWorkerDeleteImageFB(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters), KoinComponent {

    private val imageDeleteImageFB: ImageFbRepository by inject()

    override fun doWork(): Result {
        val resourceUriStr = inputData.getString("KEY_IMAGE_URI")
        val resourceListUser = inputData.getStringArray("LIST")
        if (resourceUriStr != null) {
            imageDeleteImageFB.deleteImage(converterStrUri(resourceUriStr))
        } else if (resourceListUser != null) {
            imageDeleteImageFB.deleteImage(converterStrListToUri(resourceListUser))
        }

        return Result.success()
    }

    private fun converterStrUri(uriStr: String?): Uri? {
        var uri: Uri? = null
        uriStr?.let {
            uri = it.toUri()
        }
        return uri
    }

    private fun converterStrListToUri(array: Array<String>): List<Uri?> {
        val listUri: MutableList<Uri> = mutableListOf()
        for (i in array.toList()) {
            listUri.add(i.toUri())
        }
        return listUri
    }
}