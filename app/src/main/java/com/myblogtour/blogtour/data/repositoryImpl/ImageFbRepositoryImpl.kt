package com.myblogtour.blogtour.data.repositoryImpl

import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.myblogtour.blogtour.domain.repository.ImageFbRepository

class ImageFbRepositoryImpl(private val storageRef: StorageReference) : ImageFbRepository {

    private var nameFile: StorageReference? = null
    private var uploadTask: UploadTask? = null

    override fun imageLoading(
        uri: Uri,
        onSuccess: (Uri) -> Unit,
        onError: (String) -> Unit,
        onProgress: ((Int)) -> Unit,
    ) {
        nameFile = storageRef.child("image/${uri.lastPathSegment}")
        uploadTask = nameFile?.let {
            it.putFile(uri)
        }
        uploadTask?.let { uploadTask ->
            with(uploadTask) {
                addOnFailureListener { er ->
                    onError.invoke(er.message!!)
                }.addOnSuccessListener {
                    getDownloadUrl(uri = {
                        onSuccess.invoke(it)
                    })
                }.addOnProgressListener {
                    val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
                    onProgress.invoke(progress.toInt())
                }
            }
        }
    }

    private fun getDownloadUrl(uri: (Uri) -> Unit) {
        uploadTask?.let { uploadTask ->
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { ex ->
                        throw ex
                    }
                }
                nameFile?.let {
                    it.downloadUrl
                }
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    uri.invoke(task.result)
                }
            }
        }
    }

    override fun deleteImage(uri: Uri) {
        Thread {
            Thread.sleep(5000)
            storageRef.child("image/${uri.lastPathSegment}").delete()
//            nameFile?.let {
//                it.delete()
//            }
        }.start()
    }
}