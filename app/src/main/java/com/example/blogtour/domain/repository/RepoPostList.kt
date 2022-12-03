package com.example.blogtour.domain.repository

import com.example.blogtour.domain.Post

interface RepoPostList {
    fun getPost(): List<Post>
}