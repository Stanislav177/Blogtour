package com.myblogtour.blogtour.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.appState.AppStateListBlog
import com.myblogtour.blogtour.databinding.FragmentHomeBinding
import com.myblogtour.blogtour.databinding.ItemRecyclerBlogCarouselBinding
import com.myblogtour.blogtour.ui.googleMaps.GoogleMapsFragment
import com.myblogtour.blogtour.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    MyOnClickListener {

    private var flag = false
    private lateinit var itemOpenMenuMore: ItemRecyclerBlogCarouselBinding

    private val postViewModel: HomeViewModel by viewModel()

    private val adapter: HomeRecyclerAdapter by lazy { HomeRecyclerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postViewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        binding.recyclerListBlog.adapter = adapter

        binding.recyclerListBlog.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
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
                Toast.makeText(requireActivity(), it.error.message, Toast.LENGTH_SHORT).show()
            }
            is AppStateListBlog.Success -> {
                adapter.setPostData(it.dataPost)
            }
        }
    }

    override fun onItemClickLike(idTableLike: String) {
        postViewModel.likePublication(idTableLike)
    }

    override fun onItemClickLikeError() {
        Toast.makeText(requireActivity(), "Необходимо авторизоваться", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClickComplaintPublication(id: String) {
        postViewModel.updatePublicationComplaint(id)
    }

    override fun onItemClickMore(item: ItemRecyclerBlogCarouselBinding) {
        moreMenuPublication(item)
    }

    override fun onItemClickLocation() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragment, GoogleMapsFragment()).commit()
    }

    private fun moreMenuPublication(item: ItemRecyclerBlogCarouselBinding) {
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