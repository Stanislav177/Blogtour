package com.myblogtour.blogtour.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.ItemRecyclerBlogCarouselBinding
import com.myblogtour.blogtour.domain.PublicationEntity

class HomeRecyclerAdapter(private var myOnClickListener: MyOnClickListener) :
    RecyclerView.Adapter<HomeRecyclerAdapter.PostViewHolder>() {

    private var listPost: MutableList<PublicationEntity> = mutableListOf()

    fun setPostData(postData: List<PublicationEntity>) {
        this.listPost = postData.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemBinding: ItemRecyclerBlogCarouselBinding =
            ItemRecyclerBlogCarouselBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return PostViewHolder(itemBinding.root)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(this.listPost[position])
    }

    override fun getItemCount() = listPost.size

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(post: PublicationEntity) {
            val imageAdapter: HomeImagePublicationRecyclerAdapter by lazy {
                HomeImagePublicationRecyclerAdapter()
            }
            ItemRecyclerBlogCarouselBinding.bind(itemView).apply {
                imageAdapter.setListImagePublication(post.urlImage)
                rvImagePublication.adapter = imageAdapter
                TabLayoutMediator(tabLayout, rvImagePublication) { tab, position ->
                    rvImagePublication.apply {
                        clipChildren = false
                        clipToPadding = false
                        // offscreenPageLimit = 1
                        (getChildAt(0) as RecyclerView).overScrollMode =
                            RecyclerView.OVER_SCROLL_NEVER
                    }
                }.attach()

                nickNameTextView.text = post.nickNameUserProfile
                location.text = post.location
                countLike.text = post.counterLike.toString()
                dateAdditionsPublication.text = post.date
                textPublicationCard.text = post.text
                iconUserProfile.load(post.iconFromUserProfile) {
                    transformations(CircleCropTransformation())
                }
                nickNameUserLike.text = post.nickNameUserLike
                if (post.maxLinesText) {
                    textPublicationCard.maxLines = 50
                } else {
                    textPublicationCard.maxLines = 3
                }
//                if (imageNUll != 0) {
//                    imagePost.load(post.urlImage[0].url) {
//                        placeholder(R.drawable.ic_load_image)
//                    }
//                }
                if (post.clickLikePublication) {
                    likePost.setImageResource(R.drawable.ic_like_on)
                } else {
                    likePost.setImageResource(R.drawable.ic_like_off)
                }

                textPublicationCard.setOnClickListener {
                    if (!post.maxLinesText) {
                        listPost[layoutPosition].maxLinesText = true
                        notifyItemChanged(layoutPosition)
                    } else {
                        listPost[layoutPosition].maxLinesText = false
                        notifyItemChanged(layoutPosition)
                    }
                }

                likePost.setOnClickListener {
                    if (post.currentUser) {
                        if (!post.clickLikePublication) {
                            val interimCount = listPost[layoutPosition].counterLike
                            listPost[layoutPosition].counterLike = interimCount + 1
                            listPost[layoutPosition].clickLikePublication = true
                            notifyItemChanged(layoutPosition)
                        } else {
                            val interimCount = listPost[layoutPosition].counterLike
                            listPost[layoutPosition].counterLike = interimCount - 1
                            listPost[layoutPosition].clickLikePublication = false
                            notifyItemChanged(layoutPosition)
                        }
                        myOnClickListener.onItemClickLike(post.idCounterLike)
                    } else {
                        myOnClickListener.onItemClickLikeError()
                    }
                }

                moreCard.setOnClickListener {
                    myOnClickListener.onItemClickMore(ItemRecyclerBlogCarouselBinding.bind(itemView)
                        .apply { this })
                    //moreMenuPublication(ItemRecyclerBlogBinding.bind(itemView).apply { this })
                }
                btnComplaintPublication.setOnClickListener {
                    //moreMenuPublication(ItemRecyclerBlogBinding.bind(itemView).apply { this })
                    myOnClickListener.onItemClickComplaintPublication(post.id)
                    myOnClickListener.onItemClickMore(ItemRecyclerBlogCarouselBinding.bind(itemView)
                        .apply { this })
                }
            }
        }
    }
}