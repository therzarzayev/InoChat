package com.therzarzayev.inochat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.therzarzayev.inochat.R
import com.therzarzayev.inochat.databinding.PostCardItemBinding
import com.therzarzayev.inochat.models.PostModel
import com.therzarzayev.inochat.utils.GlideObj.getPostImg

class PostAdapter(private val posts: ArrayList<PostModel>) :
    RecyclerView.Adapter<PostAdapter.CardViewHolder>() {
    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val user: TextView
        val date: TextView
        val postImg: ImageView
        val progressBar: ProgressBar
        init {
            user = itemView.findViewById(R.id.c_username)
            date = itemView.findViewById(R.id.date_text)
            postImg = itemView.findViewById(R.id.postImage)
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.post_card_item, parent, false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val post = posts[position]
        holder.user.text = post.user
        holder.date.text = post.date.toString()
        getPostImg(holder.itemView.context, post.downloadUrl, holder.postImg,holder.progressBar)

    }

    override fun getItemCount(): Int {
        return posts.size
    }
}