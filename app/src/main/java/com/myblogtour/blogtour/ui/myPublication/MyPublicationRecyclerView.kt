package com.myblogtour.blogtour.ui.myPublication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.myblogtour.blogtour.databinding.ItemRecyclerViewMyPublicationBinding
import com.myblogtour.blogtour.domain.PublicationEntity

class MyPublicationRecyclerView(val myOnItemClickListener: MyOnItemClickListener) :
    RecyclerView.Adapter<MyPublicationRecyclerView.ViewHolderMyPublication>() {

    private var myPublicationList: MutableList<PublicationEntity> = mutableListOf()

    fun setMyPublicationList(listMyPublication: List<PublicationEntity>) {
        this.myPublicationList = listMyPublication.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMyPublication {
        val itemBinding: ItemRecyclerViewMyPublicationBinding =
            ItemRecyclerViewMyPublicationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolderMyPublication(itemBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolderMyPublication, position: Int) {
        holder.bind(myPublicationList[position])
    }

    override fun getItemCount() = myPublicationList.size

    inner class ViewHolderMyPublication(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(publication: PublicationEntity) {
            val imageSize = publication.urlImage.size

            ItemRecyclerViewMyPublicationBinding.bind(itemView).run {
                if (imageSize != 0) {
                    imageMyPublication.load(publication.urlImage[0].url)
                }
                textViewDatePublication.text = publication.date
                textViewMyPublication.text = publication.text
                textViewCounterLikeMyPublication.text = " - ${publication.counterLike}"
                btnDeletePublication.setOnClickListener {
                    myOnItemClickListener.onClickDeletePublication(publication.id, publication.idCounterLike)
                    myPublicationList.removeAt(layoutPosition)
                    notifyItemChanged(layoutPosition)
                }
            }
        }
    }
}