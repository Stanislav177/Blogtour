package com.myblogtour.blogtour.domain.repository

import com.myblogtour.blogtour.domain.PublicationEntity

interface RepoPostList {
    fun getPost(): List<PublicationEntity>
}