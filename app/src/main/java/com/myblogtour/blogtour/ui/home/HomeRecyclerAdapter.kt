package com.myblogtour.blogtour.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.ItemRecyclerBlogBinding
import com.myblogtour.blogtour.domain.PublicationEntity

class HomeRecyclerAdapter(private var myOnClickListener: MyOnClickListener) :
    RecyclerView.Adapter<HomeRecyclerAdapter.PostViewHolder>() {

    private var listPost: MutableList<PublicationEntity> = mutableListOf()
    private var flag = false

    fun setPostData(postData: List<PublicationEntity>) {
        this.listPost = postData.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemBinding: ItemRecyclerBlogBinding = ItemRecyclerBlogBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(itemBinding.root)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(this.listPost[position])
    }

    override fun getItemCount() = listPost.size

    inner class PostViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(post: PublicationEntity) {
            val imageNUll = post.urlImage.size

            ItemRecyclerBlogBinding.bind(itemView).apply {
                //moreMenuPublication.alpha = 0f
                nickNameTextView.text = post.nickNameUserProfile
                countLike.text = post.counterLike.toString()
                dateAdditionsPublication.text = post.date
                textPublicationCard.text = post.text
                iconUserProfile.load(post.iconFromUserProfile)
                nickNameUserLike.text = post.nickNameUserLike
                if (post.maxLinesText) {
                    textPublicationCard.maxLines = 50
                } else {
                    textPublicationCard.maxLines = 3
                }

                if (imageNUll != 0) {
                    imagePost.load(post.urlImage[0].url)
                }
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
                        myOnClickListener.onItemClickLike(
                            post.idCounterLike
                        )
                    } else {
                        myOnClickListener.onItemClickLikeError()
                    }
                }

                moreCard.setOnClickListener {
                    myOnClickListener.onItemClickMore(
                        ItemRecyclerBlogBinding.bind(itemView).apply { this })
                    //moreMenuPublication(ItemRecyclerBlogBinding.bind(itemView).apply { this })
                }
                btnComplaintPublication.setOnClickListener {
                    //moreMenuPublication(ItemRecyclerBlogBinding.bind(itemView).apply { this })
                    myOnClickListener.onItemClickComplaintPublication(post.id)
                    myOnClickListener.onItemClickMore(
                        ItemRecyclerBlogBinding.bind(itemView).apply { this })
                }
            }
        }

        private fun moreMenuPublication(item: ItemRecyclerBlogBinding) {
            flag = !flag
            if (flag) {
                ObjectAnimator.ofFloat(item.moreMenuPublication, View.TRANSLATION_Y, 0f, 130f)
                    .setDuration(1000).start()
                item.moreMenuPublication.animate().alpha(1f).setDuration(1000).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            item.moreMenuPublication.isClickable = true
                            super.onAnimationEnd(animation)
                        }
                    }
                )
            } else {
                ObjectAnimator.ofFloat(item.moreMenuPublication, View.TRANSLATION_Y, 130f, 0f)
                    .setDuration(1000).start()
                item.moreMenuPublication.animate().alpha(0f).setDuration(1000).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            item.moreMenuPublication.isClickable = false
                            super.onAnimationEnd(animation)
                        }
                    }
                )
            }
        }
    }
}