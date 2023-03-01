package com.myblogtour.blogtour.ui.home

import com.myblogtour.blogtour.databinding.ItemRecyclerBlogBinding

interface MyOnClickListener {
    fun onItemClickLike(error: Boolean)
    fun onItemClickLike(idTableLike: String, like: Boolean)
    fun onItemClickComplaintPublication(id: String)
    fun onItemClickMore(item: ItemRecyclerBlogBinding)
}