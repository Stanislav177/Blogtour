package com.myblogtour.blogtour.domain.repository

interface RepoRegistrationUserFb {
    fun correctEmail(email: String?, password: String?)
}