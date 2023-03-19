package com.myblogtour.blogtour.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.myblogtour.blogtour.R
import com.myblogtour.blogtour.databinding.ItemRecyclerViewResultSearchBinding
import com.myblogtour.blogtour.domain.PublicationEntity

class ResultRecyclerAdapter(private var myOnClickListener: OnMyClickListener) :
    RecyclerView.Adapter<ResultRecyclerAdapter.ResultViewHolder>() {

    private var listPublication: MutableList<PublicationEntity> = mutableListOf()

    fun setListPublication(list: List<PublicationEntity>) {
        this.listPublication = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemBinding = ItemRecyclerViewResultSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ResultViewHolder(itemBinding.root)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(this.listPublication[position])
    }

    override fun getItemCount() = listPublication.size

    inner class ResultViewHolder(view: View) : ViewHolder(view) {
        fun bind(publication: PublicationEntity) {
            val imageNUll = publication.urlImage.size
            ItemRecyclerViewResultSearchBinding.bind(itemView).apply {
                nickNameTextViewResultSearch.text = publication.nickNameUserProfile
                locationResultSearch.text = publication.location
                countLikeResultSearch.text = publication.counterLike.toString()
                dateAdditionsPublicationResultSearch.text = publication.date
                textPublicationCardResultSearch.text = publication.text
                iconUserProfileResultSearch.load(publication.iconFromUserProfile)
                nickNameUserLikeResultSearch.text = publication.nickNameUserLike
                if (publication.maxLinesText) {
                    textPublicationCardResultSearch.maxLines = 50
                } else {
                    textPublicationCardResultSearch.maxLines = 3
                }

                if (imageNUll != 0) {
                    imagePostResultSearch.load(publication.urlImage[0].url)
                }
                if (publication.clickLikePublication) {
                    likePostResultSearch.setImageResource(R.drawable.ic_like_on)
                } else {
                    likePostResultSearch.setImageResource(R.drawable.ic_like_off)
                }

                textPublicationCardResultSearch.setOnClickListener {
                    if (!publication.maxLinesText) {
                        listPublication[layoutPosition].maxLinesText = true
                        notifyItemChanged(layoutPosition)
                    } else {
                        listPublication[layoutPosition].maxLinesText = false
                        notifyItemChanged(layoutPosition)
                    }
                }

                likePostResultSearch.setOnClickListener {
                    if (publication.currentUser) {
                        if (!publication.clickLikePublication) {
                            val interimCount = listPublication[layoutPosition].counterLike
                            listPublication[layoutPosition].counterLike = interimCount + 1
                            listPublication[layoutPosition].clickLikePublication = true
                            notifyItemChanged(layoutPosition)
                        } else {
                            val interimCount = listPublication[layoutPosition].counterLike
                            listPublication[layoutPosition].counterLike = interimCount - 1
                            listPublication[layoutPosition].clickLikePublication = false
                            notifyItemChanged(layoutPosition)
                        }
                        myOnClickListener.onItemClickLike(
                            publication.idCounterLike
                        )
                    } else {
//                        myOnClickListener.onItemClickLikeError()
                    }
                }

                moreCardResultSearch.setOnClickListener {
                    myOnClickListener.onItemClickMore(
                        ItemRecyclerViewResultSearchBinding.bind(itemView).apply { this }
                    )
                }
                btnComplaintPublicationResultSearch.setOnClickListener {
                    myOnClickListener.onItemClickComplaintPublication(publication.id)
                    myOnClickListener.onItemClickMore(
                        ItemRecyclerViewResultSearchBinding.bind(itemView).apply { this })
                }
            }
        }
    }
}