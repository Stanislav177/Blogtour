package com.myblogtour.blogtour.ui.addPost

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.myblogtour.airtable.data.RepoAirTablePostingImpl
import com.myblogtour.airtable.domain.Records
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPostViewModel : ViewModel(), AddContract.ViewModel {

    private val repoAirTable: RepoAirTablePostingImpl by lazy {
        RepoAirTablePostingImpl()
    }

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val storageRef: StorageReference by lazy {
        FirebaseStorage.getInstance().reference
    }

    private var nameFile: StorageReference? = null
    private var uploadTask: UploadTask? = null
    private val currentUser = auth.currentUser

    override val publishPostLiveData: LiveData<Boolean> = MutableLiveData()
    override val loadUri: LiveData<Uri?> = MutableLiveData()
    override val progressLoad: LiveData<Int> = MutableLiveData()

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

    override fun dataPost(
        text: String,
        location: String,
        imageUri: Uri,
    ) {
        val urlImage = JsonArray()
        val image = JsonObject()
        val post = JsonObject()
        val publishPost = JsonObject()

        image.addProperty("url", imageUri.toString())
        urlImage.add(image)

        with(post) {
            addProperty("nickName", currentUser!!.displayName)
            addProperty("text", text)
            addProperty("location", location)
            addProperty("likeCount", 0)
            add("urlImage", urlImage)
        }
        publishPost.add("fields", post)

        repoAirTable.createPostAirTable(publishPost, callback)
    }

    private val callback = object : Callback<Records> {
        override fun onResponse(call: Call<Records>, response: Response<Records>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    publishPostLiveData.mutable().postValue(true)
                }
            } else {

            }
        }

        override fun onFailure(call: Call<Records>, t: Throwable) {
            val tM = t.message
        }
    }

    private fun converterJsonObject(
        nickName: String,
        text: String,
        location: String,
    ): JsonObject {
        val post = JsonObject()
        val publishPost = JsonObject()
        with(post) {
            addProperty("nickName", nickName)
            addProperty("text", text)
            addProperty("location", location)
        }
        publishPost.add("field", post)
        return publishPost
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("Error LiveData")
    }
}