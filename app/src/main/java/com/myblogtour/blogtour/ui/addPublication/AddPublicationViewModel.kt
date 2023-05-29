package com.myblogtour.blogtour.ui.addPublication

import android.net.Uri
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.myblogtour.blogtour.domain.ImagePublicationEntity
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.domain.repository.CreatePublicationRepository
import com.myblogtour.blogtour.domain.repository.ImageFbRepository
import com.myblogtour.blogtour.utils.SingleLiveEvent
import com.myblogtour.blogtour.utils.checkPermission.RepositoryLocationAddress

class AddPublicationViewModel(
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val createPublicationRepository: CreatePublicationRepository,
    private val locationAddressRepository: RepositoryLocationAddress,
    private val imageFbRepository: ImageFbRepository,
) : ViewModel(), AddContract.ViewModel {

    private var flagAddPublication = true
    private var loadingImagePublication = true
    private lateinit var imagePublicationEntity: ImagePublicationEntity
    private var listImagePublication: MutableList<ImagePublicationEntity> = mutableListOf()

    override val publishPostLiveData: LiveData<Boolean> = MutableLiveData()
    override val loadUriImage: LiveData<ImagePublicationEntity> = MutableLiveData()
    override val progressLoad: LiveData<ImagePublicationEntity> = MutableLiveData()
    override val errorMessageImage: LiveData<String> = MutableLiveData()
    override val errorMessageText: LiveData<String> = MutableLiveData()
    override val errorMessageLocation: LiveData<String> = MutableLiveData()
    override val errorMessagePublicationAdd: LiveData<String> = SingleLiveEvent()
    override val address: LiveData<Editable> = SingleLiveEvent()
    override val errorAddress: LiveData<String> = SingleLiveEvent()
    override val counterImage: LiveData<Boolean> = SingleLiveEvent()
    override val loadingImage: LiveData<Boolean> = SingleLiveEvent()
    override val amountImage: LiveData<Int> = SingleLiveEvent()
    override val loadingImageFb: LiveData<Uri> = SingleLiveEvent()

    fun flagAddPublication(b: Boolean) {
        flagAddPublication = b
    }

    fun getCounterImage() {
        if (listImagePublication.size == 3) {
            return counterImage.mutable().postValue(false)
        }
        return counterImage.mutable().postValue(true)
    }

    fun getLoadingImage() {
        loadingImage.mutable().postValue(loadingImagePublication)
    }

    fun cancel(uri: Uri) {
        imageFbRepository.cancelLoading(uri)
    }

    fun deleteImage() {
        val uriList: MutableList<Uri?> = mutableListOf()
        for (i in listImagePublication.indices) {
            when (i) {
                0 -> {
                    uriList.add(listImagePublication[i].uriLocal)
                }
                1 -> {
                    uriList.add(listImagePublication[i].uriLocal)
                }
                2 -> {
                    uriList.add(listImagePublication[i].uriLocal)
                }
            }
        }
        if (uriList.size > 0) {
            imageFbRepository.deleteImage(uriList.toList())
        }
        listImagePublication.clear()
        amountImage.mutable().postValue(listImagePublication.size)
    }

    fun deleteImage(uriImage: Uri?) {
        uriImage?.let {
            imageFbRepository.deleteImage(it)
        }
        listImagePublication.indices.find {
            listImagePublication[it].uriLocal == uriImage
        }?.let { index ->
            listImagePublication.removeAt(index)
        }
        amountImage.mutable().postValue(listImagePublication.size)
    }

    private fun checkImageList(uri: Uri): Boolean {
        return if (listImagePublication.size > 0) {
            val checkImage = listImagePublication.indices.find {
                listImagePublication[it].uriLocal == uri
            }
            checkImage == null
        } else {
            true
        }
        return false
    }

    fun image(uri: Uri) {
        if (checkImageList(uri)) {
            loadingImageFb.mutable().postValue(uri)
            loadingImagePublication = false
            imageFbRepository.imageLoading(uri,
                onSuccess = { uriFb ->
                    imagePublicationEntity = ImagePublicationEntity(uriFb, uri, false)
                    listImagePublication.add(imagePublicationEntity)
                    loadUriImage.mutable().postValue(imagePublicationEntity)
                    loadingImagePublication = true
                    amountImage.mutable().postValue(listImagePublication.size)
                }, onError = {

                }, onProgress = { onProgress ->
                    imagePublicationEntity =
                        ImagePublicationEntity(uriLocal = uri, progress = onProgress)
                    progressLoad.mutable().postValue(imagePublicationEntity)
                })
        }
    }

    override fun dataPublication(
        text: String,
        location: String,
    ) {
        if (flagAddPublication) {
            when {
                listImagePublication.size == 0 -> {
                    errorMessageImage.mutable().postValue("Добавьте изображение")
                    return
                }
                text.isEmpty() -> {
                    errorMessageImage.mutable().postValue("Добавьте текст")
                    return
                }
                location.isEmpty() -> {
                    errorMessageImage.mutable().postValue("Добавьте местоположение")
                    return
                }
                else -> {
                    flagAddPublication = false
                    val publishPost =
                        converterJsonObject(
                            text,
                            location)
                    createPublicationRepository.createPublication(onSuccess = {
                        publishPostLiveData.mutable().postValue(it)
                    }, onError = {
                        errorMessagePublicationAdd.mutable().postValue(it.message)
                    }, publishPost)
                }
            }
        }
    }

    override fun getAddress(lat: Double?, lon: Double?) {
        locationAddressRepository.getAddress(lat, lon, onAddress = {
            address.mutable().postValue(it.toEditable())
        }, errorAddress = {
            errorAddress.mutable().postValue(it)
        })
    }

    private fun converterJsonObject(
        text: String,
        location: String,
    ): JsonObject {
        val userProfile = JsonArray()
        var userIdProfile = ""
        authFirebaseRepository.userCurrent(onSuccess = {
            it.let {
                userIdProfile = it.displayName!!
            }
        }, onError = {

        })
        val publicationJson = JsonObject()
        val fieldsJson = JsonObject()
        userProfile.add(userIdProfile)
        with(publicationJson) {
            addProperty("text", text)
            addProperty("location", location)
            add("image", converterFromListToJson())
            add("userprofile", userProfile)
        }
        fieldsJson.add("fields", publicationJson)
        return fieldsJson
    }

    private fun converterFromListToJson(): JsonArray {
        val urlImage = JsonArray()
        val image = JsonObject()
        val imageTwo = JsonObject()
        val imageThree = JsonObject()
        for (i in listImagePublication.indices) {
            when (i) {
                0 -> {
                    image.addProperty("url", listImagePublication[i].url.toString())
                    urlImage.add(image)
                }
                1 -> {
                    imageTwo.addProperty("url", listImagePublication[i].url.toString())
                    urlImage.add(imageTwo)
                }
                2 -> {
                    imageThree.addProperty("url", listImagePublication[i].url.toString())
                    urlImage.add(imageThree)
                }
            }
        }
        return urlImage
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("Error LiveData")
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}