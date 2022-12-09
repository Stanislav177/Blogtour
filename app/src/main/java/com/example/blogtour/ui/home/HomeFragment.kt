package com.example.blogtour.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.blogtour.appState.AppStateListBlog
import com.example.blogtour.databinding.FragmentHomeBinding
import com.example.blogtour.utils.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val postViewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private val adapter: HomeRecyclerAdapter by lazy {
        HomeRecyclerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postViewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        binding.recyclerListBlog.adapter = adapter

        postViewModel.getPostList()
    }

    private fun renderData(it: AppStateListBlog) {
        when (it) {
            is AppStateListBlog.Error -> {

            }
            is AppStateListBlog.Success -> {
                adapter.setPostData(it.dataPost)
            }
        }
    }

}