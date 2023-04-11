package com.myblogtour.blogtour.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.GsonBuilder
import com.myblogtour.blogtour.data.repositoryImpl.*
import com.myblogtour.blogtour.data.retrofit.AirTableApi
import com.myblogtour.blogtour.domain.repository.*
import com.myblogtour.blogtour.ui.addPublication.AddPublicationViewModel
import com.myblogtour.blogtour.ui.authUser.AuthUserViewModel
import com.myblogtour.blogtour.ui.home.HomeViewModel
import com.myblogtour.blogtour.ui.main.MainViewModel
import com.myblogtour.blogtour.ui.myPublication.MyPublicationViewModel
import com.myblogtour.blogtour.ui.noNetworkConnection.NoNetworkConnectionViewModel
import com.myblogtour.blogtour.ui.profileUser.ProfileViewModel
import com.myblogtour.blogtour.ui.profileUser.resetPassword.ViewModelResetPassword
import com.myblogtour.blogtour.ui.recoveryPassword.RecoveryPasswordViewModel
import com.myblogtour.blogtour.ui.registrationUser.RegistrationViewModel
import com.myblogtour.blogtour.ui.search.ResultSearchViewModel
import com.myblogtour.blogtour.utils.networkConnection.NetworkStatusRepository
import com.myblogtour.blogtour.utils.networkConnection.NetworkStatusRepositoryImpl
import com.myblogtour.blogtour.utils.validatorEmail.EmailValidatorPattern
import com.myblogtour.blogtour.utils.validatorEmail.EmailValidatorPatternImpl
import com.myblogtour.blogtour.utils.validatorPassword.PasswordValidatorPattern
import com.myblogtour.blogtour.utils.validatorPassword.PasswordValidatorPatternImpl
import com.myblogtour.blogtour.utils.validatorUserName.LoginValidatorPattern
import com.myblogtour.blogtour.utils.validatorUserName.LoginValidatorPatternImpl
import org.koin.android.ext.koin.androidContext
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
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                ).build()
        }
        single<AirTableApi>(named("api")) {
            get<Retrofit>(named("retrofit")).create(AirTableApi::class.java)
        }
        single<UserProfileRepository> {
            UserProfileRepositoryImpl(get(named("api")), get(named("authFirebase")))
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
        single<SearchPublication> {
            SearchPublicationImpl(get(named("api")))
        }
        single<PasswordValidatorPattern> {
            PasswordValidatorPatternImpl()
        }
        single<EmailValidatorPattern> {
            EmailValidatorPatternImpl()
        }
        single<LoginValidatorPattern> {
            LoginValidatorPatternImpl()
        }
        single<NetworkStatusRepository> {
            NetworkStatusRepositoryImpl(androidContext())
        }
    }

    val firebase = module {
        single(named("authFirebase")) { Firebase.auth }
        single(named("storageRef")) { FirebaseStorage.getInstance().reference }
    }

    val viewModelsModule = module {
        viewModel {
            ProfileViewModel(get(), get(), get())
        }
        viewModel {
            HomeViewModel(publicationRepository = get(), authFirebaseRepository = get())
        }
        viewModel {
            MyPublicationViewModel(get(), get())
        }
        viewModel {
            AddPublicationViewModel(
                get(),
                get(named("storageRef")),
                get()
            )
        }
        viewModel {
            AuthUserViewModel(authFirebaseRepository = get())
        }
        viewModel {
            RegistrationViewModel(
                authFirebaseRepository = get(),
                userRegistrationRepository = get(),
                storageRef = get(named("storageRef")),
                validPasswordPattern = get(),
                validEmailPattern = get(),
                validNameValidatorPattern = get()
            )
        }
        viewModel {
            MainViewModel(
                authFirebaseRepository = get(),
                networkStatusRepository = get()
            )
        }
        viewModel {
            RecoveryPasswordViewModel(get())
        }

        viewModel {
            ViewModelResetPassword(get(named("authFirebase")), get())
        }
        viewModel {
            ResultSearchViewModel(get(), get())
        }
        viewModel {
            NoNetworkConnectionViewModel(get())
        }
    }
}


