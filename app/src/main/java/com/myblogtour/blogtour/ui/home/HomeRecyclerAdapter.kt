package com.myblogtour.blogtour.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.domain.PublicationEntity

class HomeRecyclerAdapter(private var myOnClickListener: MyOnClickListener) :
    RecyclerView.Adapter<HomeRecyclerAdapter.PostViewHolder>() {

    private var listPost: List<PublicationEntity> = listOf()

    fun setPostData(postData: List<PublicationEntity>) {
        this.listPost = postData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_blog, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(this.listPost[position])
    }

    override fun getItemCount() = listPost.size

    inner class PostViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(post: PublicationEntity) {
            val imageNUll = post.urlImage?.let { it.size }

            with(itemView) {
                findViewById<TextView>(R.id.nickNameTextView).text = post.nickNameUserProfile
                findViewById<TextView>(R.id.countLike).text =
                    post.counterLikeFromCounterLike.toString()
                findViewById<TextView>(R.id.dateAdditionsBlog).text = post.date
                findViewById<TextView>(R.id.textPostCard).text = post.text
                findViewById<ImageView>(R.id.iconUserProfile).load(post.iconFromUserProfile)
                findViewById<TextView>(R.id.nickNameUserLike).text =
                    post.nickNameUserLike.toString()

                if (imageNUll != 0) {
                    findViewById<ImageView>(R.id.imagePost).load(post.urlImage[0].url)
                }
                if (post.clickLikePublication) {
                    findViewById<ImageView>(R.id.likePost).setImageResource(R.drawable.ic_like_on)
                }

                findViewById<ImageView>(R.id.likePost).setOnClickListener {
                    myOnClickListener.onItemClick(post.idcounterlike,post.clickLikePublication)
                }
            }
        }
    }
}