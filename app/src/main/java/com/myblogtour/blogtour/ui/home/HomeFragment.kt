package com.myblogtour.blogtour.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.myblogtour.blogtour.appState.AppStateListBlog
import com.myblogtour.blogtour.databinding.FragmentHomeBinding
import com.myblogtour.blogtour.databinding.ItemRecyclerBlogBinding
import com.myblogtour.blogtour.utils.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    MyOnClickListener {

    private var flag = false
    private lateinit var itemOpenMenuMore: ItemRecyclerBlogBinding
    private val postViewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private val adapter: HomeRecyclerAdapter by lazy { HomeRecyclerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postViewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        binding.recyclerListBlog.adapter = adapter


        binding.recyclerListBlog.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (flag) {
                if (scrollY > oldScrollY) {
                    moreMenuPublication(itemOpenMenuMore)
                } else {
                    moreMenuPublication(itemOpenMenuMore)
                }
            }
        }
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

    override fun onItemClick(idTableLike: String, like: Boolean) {
        postViewModel.likePublication(idTableLike)
        Toast.makeText(context, idTableLike, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClickComplaintPublication(id: String) {
        postViewModel.updatePublicationComplaint(id, "recWOHgeC8AlH04cD")
    }

    override fun onItemClickMore(item: ItemRecyclerBlogBinding) {
        moreMenuPublication(item)
    }

    private fun moreMenuPublication(item: ItemRecyclerBlogBinding) {
        flag = !flag
        itemOpenMenuMore = item
        if (flag) {
            itemOpenMenuMore.moreMenuPublication.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(
                itemOpenMenuMore.moreMenuPublication,
                View.TRANSLATION_Y,
                0f,
                130f
            )
                .setDuration(1000).start()
            itemOpenMenuMore.moreMenuPublication.animate().alpha(1f).setDuration(1000).setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        itemOpenMenuMore.moreMenuPublication.isClickable = true
                        super.onAnimationEnd(animation)
                    }
                }
            )
        } else {
            ObjectAnimator.ofFloat(
                itemOpenMenuMore.moreMenuPublication,
                View.TRANSLATION_Y,
                130f,
                0f
            )
                .setDuration(1000).start()
            itemOpenMenuMore.moreMenuPublication.animate().alpha(0f).setDuration(1000).setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        itemOpenMenuMore.moreMenuPublication.visibility = View.GONE
                        itemOpenMenuMore.moreMenuPublication.isClickable = false
                        super.onAnimationEnd(animation)
                    }
                }
            )
        }
    }
}