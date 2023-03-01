package com.myblogtour.blogtour.ui.addPublication

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.myblogtour.blogtour.domain.repository.CreatePublicationRepository

class AddPublicationViewModel(
    private val user: FirebaseUser?,
    private val storageRef: StorageReference,
    private val createPublicationRepository: CreatePublicationRepository
) : ViewModel(), AddContract.ViewModel {

    private var nameFile: StorageReference? = null
    private var uploadTask: UploadTask? = null

    override val publishPostLiveData: LiveData<Boolean> = MutableLiveData()
    override val loadUri: LiveData<Uri?> = MutableLiveData()
    override val progressLoad: LiveData<Int> = MutableLiveData()
    override val errorMessage: LiveData<String> = MutableLiveData()

    fun deleteImage() {
        nameFile!!.delete().addOnSuccessListener {
            loadUri.mutable().postValue(null)
        }.addOnFailureListener {
            val er = it
        }
    }

    fun deleteImageDetach() {
        nameFile?.let {
            it.delete()
        }
    }

    fun image(uri: Uri) {
        deleteImageDetach()
        nameFile = storageRef.child("image/${uri.lastPathSegment}")
        uploadTask = nameFile?.let {
            it.putFile(uri)
        }

        uploadTask?.let { uploadTask ->
            with(uploadTask) {
                addOnFailureListener { er ->
                    val error = er
                }.addOnSuccessListener {
                    getDownloadUrl()
                }.addOnProgressListener {
                    val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
                    progressLoad.mutable().postValue(progress.toInt())
                }
            }
        }
    }

    private fun getDownloadUrl() {
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
                    loadUri.mutable().postValue(task.result)
                }
            }
        }
    }

    override fun dataPublication(
        text: String,
        location: String,
        imageUri: Uri?,
    ) {
        if (imageUri == null) {
            errorMessage.mutable().postValue("Добавьте изображение")
            return
        }
        val publishPost = converterJsonObject(imageUri, text, location)
        createPublicationRepository.createPublication(
            onSuccess = {
                publishPostLiveData.mutable().postValue(it)
            },
            onError = {

            },
            publishPost
        )
    }

    private fun converterJsonObject(imageUri: Uri?, text: String, location: String): JsonObject {
        val userProfile = JsonArray()
        var userIdProfile = ""
        user?.let {
            userIdProfile = it.displayName!!
        }
        val urlImage = JsonArray()
        val image = JsonObject()
        val publicationJson = JsonObject()
        val fieldsJson = JsonObject()

        image.addProperty("url", imageUri.toString())
        urlImage.add(image)
        userProfile.add(userIdProfile)
        with(publicationJson) {
            addProperty("text", text)
            addProperty("location", location)
            add("image", urlImage)
            add("userprofile", userProfile)
        }
        fieldsJson.add("fields", publicationJson)
        return fieldsJson
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("Error LiveData")
    }
}