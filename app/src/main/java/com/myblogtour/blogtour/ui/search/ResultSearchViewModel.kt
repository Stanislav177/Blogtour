package com.myblogtour.blogtour.ui.search

import androidx.lifecycle.ViewModel
import com.myblogtour.airtable.domain.PublicationDTO
import com.myblogtour.blogtour.domain.repository.SearchPublication

class ResultSearchViewModel(private val searchPublication: SearchPublication) : ViewModel() {

    fun getSearchPublication(search: String) {
        searchPublication.getSearchPublication(search,
            onSuccess = {
                searchPublicationVM(it)
            },
            onError = {
                val e = it
            })
    }

    private fun searchPublicationVM(dto: PublicationDTO) {
        val dtoSize = dto.records.size
    }
}