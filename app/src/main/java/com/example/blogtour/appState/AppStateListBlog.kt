package com.example.blogtour.appState

import com.example.blogtour.domain.Post

sealed class AppStateListBlog {
    data class Success(val dataPost: List<Post>) : AppStateListBlog()
    data class Error(val error: Throwable) : AppStateListBlog()
}