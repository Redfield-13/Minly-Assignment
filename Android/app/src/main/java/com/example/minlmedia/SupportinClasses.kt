package com.example.minlmedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class CardItem(
    val imageUrl: String,
    val title: String
)

class CardAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)


        fun bind(item: Item) {
            Glide.with(itemView.context)
                .load(item.file_link)
                .placeholder(R.drawable.navlogo) // Placeholder image while loading
                .error(R.drawable.navlogo) // Error image if loading fails
                .into(imageView)

            textViewTitle.text = "Posted By : " +item.author
        }
    }
}




data class Item(val id: Int, var file_link: String, val author: String,  val likes: Int)

