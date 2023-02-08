package com.myblogtour.blogtour.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.myblogtour.airtable.data.RepoAirTableImpl
import com.myblogtour.airtable.domain.UserProfileDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel(), ProfileContract.ProfileViewModel {

    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val userCurrent = auth.currentUser
    private val repoAirTable: RepoAirTableImpl by lazy { RepoAirTableImpl() }

    override val userSuccess: LiveData<FirebaseUser> = MutableLiveData()
    override val userError: LiveData<Throwable> = MutableLiveData()
    override val userSingOut: LiveData<Boolean> = MutableLiveData()

    override fun onRefresh() {
        onLoadUserProfile()
    }

    override fun singInOut() {
        auth.signOut()
        userSingOut.mutable().postValue(true)
    }

    private fun onLoadUserProfile() {
        // userSuccess.mutable().postValue(userCurrent)
        val formulaUid = "uid="
        val str = userCurrent!!.uid
        val uid = "$formulaUid'$str'"
        repoAirTable.getUserProfile(uid, callback)
    }


    private val callback = object : Callback<UserProfileDTO> {
        override fun onResponse(call: Call<UserProfileDTO>, response: Response<UserProfileDTO>) {
            if (response.isSuccessful) {
                val b = response.body()!!.records
            }
        }

        override fun onFailure(call: Call<UserProfileDTO>, t: Throwable) {
            val ex = t.message
        }

    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T> ?: throw IllegalStateException("It is not Mutable")
    }


}