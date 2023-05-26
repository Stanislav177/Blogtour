package com.myblogtour.blogtour.ui.addPublication

import android.net.Uri
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.StorageReference
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.domain.repository.CreatePublicationRepository
import com.myblogtour.blogtour.domain.repository.ImageFbRepository
import com.myblogtour.blogtour.utils.SingleLiveEvent
import com.myblogtour.blogtour.utils.checkPermission.RepositoryLocationAddress

class AddPublicationViewModel(
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val storageRef: StorageReference,
    private val createPublicationRepository: CreatePublicationRepository,
    private val locationAddressRepository: RepositoryLocationAddress,
    private val imageFbRepository: ImageFbRepository,
) : ViewModel(), AddContract.ViewModel {

    private var flagAddPublication = true
    private var counterImagePublication = 0
    private var loadingImagePublication = true

    override val publishPostLiveData: LiveData<Boolean> = MutableLiveData()
    override val loadUriOneImage: LiveData<Uri?> = MutableLiveData()
    override val loadUriTwoImage: LiveData<Uri?> = MutableLiveData()
    override val loadUriThreeImage: LiveData<Uri?> = MutableLiveData()
    override val progressLoad: LiveData<Int> = MutableLiveData()
    override val errorMessageImage: LiveData<String> = MutableLiveData()
    override val errorMessageText: LiveData<String> = MutableLiveData()
    override val errorMessageLocation: LiveData<String> = MutableLiveData()
    override val errorMessagePublicationAdd: LiveData<String> = SingleLiveEvent()
    override val address: LiveData<Editable> = SingleLiveEvent()
    override val errorAddress: LiveData<String> = SingleLiveEvent()
    override val counterImage: LiveData<Boolean> = SingleLiveEvent()

    fun flagAddPublication(b: Boolean) {
        flagAddPublication = b
    }

    fun counterImage() {
        if (counterImagePublication == 3) {
            counterImage.mutable().postValue(false)
        }
        return counterImage.mutable().postValue(true)
    }

    fun deleteImage(uriImageOne: Uri?, uriImageTwo: Uri?, uriImageThree: Uri?) {
        val uriList: MutableList<Uri?> = mutableListOf()
        with(uriList) {
            add(uriImageOne)
            add(uriImageTwo)
            add(uriImageThree)
        }
        imageFbRepository.deleteImage(uriList.toList())
    }

    fun deleteImage(uriImage: Uri?) {
        uriImage?.let {
            imageFbRepository.deleteImage(it)
        }
    }

    fun image(uri: Uri, imageNumber: Int) {
        imageFbRepository.imageLoading(uri, onSuccess = {
            when (imageNumber) {
                1 -> {
                    loadUriOneImage.mutable().postValue(it)
                }
                2 -> {
                    loadUriTwoImage.mutable().postValue(it)
                }
                3 -> {
                    loadUriThreeImage.mutable().postValue(it)
                }
            }

        }, onError = {

        }, onProgress = {
            progressLoad.mutable().postValue(it)
        })
    }

    override fun dataPublication(
        text: String,
        location: String,
        uriImageOne: Uri?,
        uriImageTwo: Uri?,
        uriImageThree: Uri?,
    ) {
        if (flagAddPublication) {
            when {
                imageUriNotNull(uriImageOne, uriImageTwo, uriImageThree) == null -> {
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
                            uriImageOne,
                            uriImageTwo,
                            uriImageThree,
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

    private fun imageUriNotNull(
        uriImageOne: Uri?,
        uriImageTwo: Uri?,
        uriImageThree: Uri?,
    ): Uri? {
        if (uriImageOne != null || uriImageTwo != null || uriImageThree != null) {
            return Uri.parse("")
        }
        return null
    }

    private fun converterJsonObject(
        uriImageOne: Uri?,
        uriImageTwo: Uri?,
        uriImageThree: Uri?,
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
        val urlImage = JsonArray()
        val image = JsonObject()
        val imageTwo = JsonObject()
        val imageThree = JsonObject()
        val publicationJson = JsonObject()
        val fieldsJson = JsonObject()
        uriImageOne?.let {
            image.addProperty("url", it.toString())
            urlImage.add(image)
        }
        uriImageTwo?.let {
            imageTwo.addProperty("url", it.toString())
            urlImage.add(imageTwo)
        }
        uriImageThree?.let {
            imageThree.addProperty("url", it.toString())
            urlImage.add(imageThree)
        }
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

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}