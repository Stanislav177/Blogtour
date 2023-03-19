package com.myblogtour.blogtour.domain.repository

import com.myblogtour.airtable.domain.PublicationDTO

interface SearchPublication {
    fun getSearchPublication(
        searchText: String,
        onSuccess: (PublicationDTO) -> Unit,
        onError: ((Throwable)) -> Unit
    )
}