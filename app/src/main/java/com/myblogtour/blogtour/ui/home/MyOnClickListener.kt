package com.myblogtour.blogtour.ui.home

import com.myblogtour.blogtour.databinding.ItemRecyclerBlogCarouselBinding

interface MyOnClickListener {
    fun onItemClickLike(idTableLike: String)
    fun onItemClickLikeError()
    fun onItemClickComplaintPublication(id: String)
    fun onItemClickMore(item: ItemRecyclerBlogCarouselBinding)
    fun onItemClickLocation()
}