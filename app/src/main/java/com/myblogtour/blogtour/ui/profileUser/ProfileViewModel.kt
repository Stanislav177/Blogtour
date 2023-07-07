package com.myblogtour.blogtour.ui.profileUser

import android.net.Uri
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.myblogtour.blogtour.domain.ImageUserProfileEntity
import com.myblogtour.blogtour.domain.UserProfileEntity
import com.myblogtour.blogtour.domain.repository.AuthFirebaseRepository
import com.myblogtour.blogtour.domain.repository.ImageFbRepository
import com.myblogtour.blogtour.domain.repository.UserProfileRepository
import com.myblogtour.blogtour.utils.SingleLiveEvent
import com.myblogtour.blogtour.utils.validatorUserName.LoginValidatorPattern

class ProfileViewModel(
    private val userProfileRepository: UserProfileRepository,
    private val authFirebaseRepository: AuthFirebaseRepository,
    private val validNameValidatorPattern: LoginValidatorPattern,
    private val imageFbRepository: ImageFbRepository,
) : ViewModel(),
    ProfileContract.ProfileViewModel {

    private lateinit var imageUserProfile: ImageUserProfileEntity

    override val userSuccess: LiveData<UserProfileEntity> = MutableLiveData()
    override val userError: LiveData<Throwable> = MutableLiveData()
    override val userSingOut: LiveData<Boolean> = MutableLiveData()
    override val verificationEmail: LiveData<String> = MutableLiveData()
    override val errorSaveProfile: LiveData<String> = SingleLiveEvent()
    override val successSaveUserProfile: LiveData<String> = SingleLiveEvent()
    override val errorUserLogin: LiveData<String> = SingleLiveEvent()
    override val errorLocationUser: LiveData<String> = SingleLiveEvent()
    override val progressLoadingImage: LiveData<ImageUserProfileEntity> = MutableLiveData()
    override val onSuccessLoadingImageUser: LiveData<Uri> = MutableLiveData()

    override fun onRefresh() {
        authFirebaseRepository.userCurrent(
            onSuccess = { fbUser ->
                userProfileRepository.getUserProfile(
                    fbUser.displayName!!,
                    onSuccess = {
                        userSuccess.mutable().postValue(it)
                    },
                    onError = {
                        userError.mutable().postValue(it)
                    }
                )
            },
            onError = {
                userError.mutable().postValue(IllegalStateException(it))
            }
        )
    }

    override fun singInOut() {
        authFirebaseRepository.singInOut {
            userSingOut.mutable().postValue(it)
        }
    }

    override fun verificationEmail() {
        authFirebaseRepository.verificationEmail {
            if (it) {
                verificationEmail.mutable().postValue("Проверьте почту для подтверждения Email")
            } else {
                verificationEmail.mutable().postValue("Что-то пошло не так")
            }
        }
    }

    override fun saveReadUserProfile(
        loginUser: Editable?,
        locationUser: String,
        genderUser: Int,
        dateBirth: String,
    ) {
        validNameValidatorPattern.afterTextUserName(loginUser)
        when {
            validNameValidatorPattern.validUserLogin -> {
                if (locationUser.isNotEmpty()) {
                    authFirebaseRepository.userCurrent(
                        onSuccess = { fbUser ->
                            userProfileRepository.saveUserProfile(
                                fbUser.displayName!!,
                                converterDataUserProfileToJson(
                                    loginUser.toString(),
                                    locationUser,
                                    genderUser,
                                    dateBirth
                                ),
                                onSuccess = {
                                    successSaveUserProfile.mutable()
                                        .postValue("Изменения сохранены")
                                },
                                onError = {
                                    errorSaveProfile.mutable().postValue(it.message)
                                }
                            )
                        },
                        onError = {
                            errorSaveProfile.mutable().postValue(it)
                        }
                    )
                } else {
                    errorLocationUser.mutable().postValue("Введите город")
                }
            }
            validNameValidatorPattern.nullUserLogin == null -> {
                errorUserLogin.mutable().postValue("Введите логин")
            }
            else -> {
                errorUserLogin.mutable().postValue("Некорректный логин")
            }
        }
    }

    override fun changeImageProfileUser(uri: Uri) {
        imageFbRepository.imageLoading(uri,
            onSuccess = {
                imageUserProfile = ImageUserProfileEntity(it, uri)
                onSuccessLoadingImageUser.mutable().postValue(imageUserProfile.uriLocal)
            },
            onError = {

            },
            onProgress = {
                progressLoadingImage.mutable().postValue(ImageUserProfileEntity(progress = it))
            })
    }

    private fun converterDataUserProfileToJson(
        loginUser: String,
        locationUser: String,
        genderUser: Int,
        dateBirth: String,
    ): JsonObject {
        val json = JsonObject()
        val jsonField = JsonObject()
        with(json) {
            addProperty("nickname", loginUser)
            addProperty("location", locationUser)
            addProperty("usergender", genderUser)
            addProperty("datebirth", dateBirth)
        }
        jsonField.add("fields", json)
        return jsonField
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("It is not Mutable")
    }
}