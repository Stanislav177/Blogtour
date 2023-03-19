package com.myblogtour.blogtour.appState

import com.myblogtour.blogtour.domain.PublicationEntity

sealed class AppStateSearchPublication {
    data class OnSuccess(val onSuccess: List<PublicationEntity>) : AppStateSearchPublication()
    data class OnError(val onError: String) : AppStateSearchPublication()
    data class OpenSearchFragment(val open: Boolean): AppStateSearchPublication()
    data class OnErrorLike(val error: String): AppStateSearchPublication()
}
