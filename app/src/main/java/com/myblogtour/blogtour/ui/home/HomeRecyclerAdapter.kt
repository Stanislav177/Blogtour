package com.myblogtour.blogtour.ui.home

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
            val imageNUll = post.urlImage?.let { it.size }

            ItemRecyclerBlogBinding.bind(itemView).apply {
                nickNameTextView.text = post.nickNameUserProfile
                countLike.text =
                    post.counterLikeFromCounterLike.toString()

                dateAdditionsBlog.text = post.date
                textPostCard.text = post.text
                iconUserProfile.load(post.iconFromUserProfile)
                nickNameUserLike.text = post.nickNameUserLike

                if (imageNUll != 0) {
                    imagePost.load(post.urlImage[0].url)
                }
                if (post.clickLikePublication) {
                    likePost.setImageResource(R.drawable.ic_like_on)
                }

                likePost.setOnClickListener {
                    if (!post.clickLikePublication) {
                        val interimCount = listPost[layoutPosition].counterLikeFromCounterLike
                        listPost[layoutPosition].counterLikeFromCounterLike = interimCount + 1
                        listPost[layoutPosition].clickLikePublication = true
                        notifyItemChanged(layoutPosition)
                    } else {
                        val interimCount = listPost[layoutPosition].counterLikeFromCounterLike
                        listPost[layoutPosition].counterLikeFromCounterLike = interimCount - 1
                        listPost[layoutPosition].clickLikePublication = false
                        notifyItemChanged(layoutPosition)
                    }
                    myOnClickListener.onItemClick(post.idcounterlike, post.clickLikePublication)
                }
            }
//            with(itemView) {
//                findViewById<TextView>(R.id.nickNameTextView).text = post.nickNameUserProfile
//                findViewById<TextView>(R.id.countLike).text =
//                    post.counterLikeFromCounterLike.toString()
//                findViewById<TextView>(R.id.dateAdditionsBlog).text = post.date
//                findViewById<TextView>(R.id.textPostCard).text = post.text
//                findViewById<ImageView>(R.id.iconUserProfile).load(post.iconFromUserProfile)
//                findViewById<TextView>(R.id.nickNameUserLike).text =
//                    post.nickNameUserLike.toString()
//
//                if (imageNUll != 0) {
//                    findViewById<ImageView>(R.id.imagePost).load(post.urlImage[0].url)
//                }
//                if (post.clickLikePublication) {
//                    findViewById<ImageView>(R.id.likePost).setImageResource(R.drawable.ic_like_on)
//                }
//
//                findViewById<ImageView>(R.id.likePost).setOnClickListener {
//                    myOnClickListener.onItemClick(post.idcounterlike, post.clickLikePublication)
//                }
//            }
        }
    }
}