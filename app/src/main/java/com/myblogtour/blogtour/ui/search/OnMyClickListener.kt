package com.myblogtour.blogtour.ui.search

import com.myblogtour.blogtour.databinding.ItemRecyclerViewResultSearchBinding

interface OnMyClickListener {
    fun onItemClickLike(idTableLike: String)
    fun onItemClickComplaintPublication(id: String)
    fun onItemClickMore(item: ItemRecyclerViewResultSearchBinding)
}