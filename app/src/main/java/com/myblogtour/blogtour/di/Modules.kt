package com.myblogtour.blogtour.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.myblogtour.blogtour.data.*
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.*
import com.myblogtour.blogtour.ui.addPublication.AddPublicationViewModel
import com.myblogtour.blogtour.ui.authUser.AuthUserViewModel
import com.myblogtour.blogtour.ui.home.HomeViewModel
import com.myblogtour.blogtour.ui.main.MainViewModel
import com.myblogtour.blogtour.ui.myPublication.MyPublicationViewModel
import com.myblogtour.blogtour.ui.profile.ProfileViewModel
import com.myblogtour.blogtour.ui.registrationUser.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Modules {
    private const val base_url_api = "https://api.airtable.com/"

    val networkModule = module {
        single<Retrofit>(named("retrofit")) {
            Retrofit.Builder()
                .baseUrl(base_url_api)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        single<AirTableApi>(named("api")) {
            get<Retrofit>(named("retrofit")).create(AirTableApi::class.java)
        }
        single<UserProfileRepository> {
            UserProfileRepositoryImpl(get(named("api")))
        }
        single<PublicationRepository> {
            PublicationRepositoryImpl(get(named("api")))
        }
        single<MyPublicationRepository> {
            MyPublicationRepositoryImpl(get(named("api")))
        }
        single<CreatePublicationRepository> {
            CreatePublicationRepositoryImpl(get(named("api")))
        }
        single<AuthFirebaseRepository> {
            AuthFirebaseRepositoryImpl(get(named("authFirebase")))
        }
        single<UserRegistrationRepository> {
            UserRegistrationRepositoryImpl(get(named("api")))
        }
    }

    val firebase = module {
        single(named("authFirebase")) { Firebase.auth }
        single(named("storageRef")) { FirebaseStorage.getInstance().reference }
    }

    val viewModelsModule = module {
        viewModel {
            ProfileViewModel(get(), get())
        }
        viewModel {
            HomeViewModel(publicationRepository = get(), authFirebaseRepository = get())
        }
        viewModel {
            MyPublicationViewModel(get())
        }
        viewModel {
            AddPublicationViewModel(
                get(named("userFirebase")), get(named("storageRef")), get()
            )
        }
        viewModel {
            AuthUserViewModel(authFirebaseRepository = get())
        }
        viewModel {
            RegistrationViewModel(
                authFirebaseRepository = get(),
                userRegistrationRepository = get(),
                storageRef = get(named("storageRef"))
            )
        }
        viewModel {
            MainViewModel(
                authFirebaseRepository = get()
            )
        }
    }
}


