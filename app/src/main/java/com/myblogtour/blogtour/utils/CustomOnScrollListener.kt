package com.myblogtour.blogtour.utils

import androidx.recyclerview.widget.RecyclerView

class CustomOnScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        when (newState) {
            RecyclerView.SCROLL_STATE_IDLE -> println("The RecyclerView is not scrolling")
            RecyclerView.SCROLL_STATE_DRAGGING -> println("Scrolling now")
            RecyclerView.SCROLL_STATE_SETTLING -> println("Scroll Settling")
        }
    }
}