package com.example.blogtour.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blogtour.appState.AppStateListBlog
import com.example.blogtour.data.RepoPostListImpl

class HomeViewModel(private val liveData: MutableLiveData<AppStateListBlog> = MutableLiveData()) : ViewModel() {

    private val repoPostList: RepoPostListImpl by lazy {
        RepoPostListImpl()
    }

    fun getLiveData() = liveData

    fun getPostList() {
        liveData.postValue(AppStateListBlog.Success(repoPostList.getPost()))
    }

}