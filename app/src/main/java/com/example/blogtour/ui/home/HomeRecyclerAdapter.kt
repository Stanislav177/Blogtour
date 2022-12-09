package com.example.blogtour.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.blogtour.R
import com.example.blogtour.domain.Post

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.PostViewHolder>() {

    private var listPost: List<Post> = listOf()

    fun setPostData(postData: List<Post>) {
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
        fun bind(post: Post) {
            val imageNUll = post.urlImage?.let { it.size }
            with(itemView) {
                findViewById<TextView>(R.id.nickNameTextView).text = post.nickName
                findViewById<TextView>(R.id.countLike).text = post.likeCount.toString()
                findViewById<TextView>(R.id.dateAdditionsBlog).text = post.dateAddition
                findViewById<TextView>(R.id.textPostCard).text = post.text

                if (imageNUll != 0) {
                    findViewById<ImageView>(R.id.imagePost).load(post.urlImage!![0].url)
                }
            }

        }
    }
}