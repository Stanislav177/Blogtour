package com.myblogtour.blogtour.appState

import com.myblogtour.blogtour.domain.PublicationEntity

sealed class AppStateListBlog {
    data class Success(val dataPost: List<PublicationEntity>) : AppStateListBlog()
    data class Error(val error: Throwable) : AppStateListBlog()
}