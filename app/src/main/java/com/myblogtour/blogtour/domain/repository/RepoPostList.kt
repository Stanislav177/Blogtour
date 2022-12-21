package com.myblogtour.blogtour.domain.repository

import com.myblogtour.blogtour.domain.Post

interface RepoPostList {
    fun getPost(): List<Post>
}