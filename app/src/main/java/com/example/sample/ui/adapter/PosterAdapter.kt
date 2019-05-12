package com.example.sample.ui.adapter

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.base.BaseViewHolder
import com.example.sample.databinding.ViewHolderPosterBinding
import com.example.sample.pojo.Poster
import com.example.sample.pojo.User

class PostAdapter(var posters: ArrayList<Poster>, private val onItemClickLister: OnItemClickLister) :
    RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ViewHolderPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClickLister
        )
    }

    override fun getItemCount(): Int = posters.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(posters[position])
    }

    override fun onViewDetachedFromWindow(holder: PostViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unBind()
    }
}

class PostViewHolder(private val binding: ViewHolderPosterBinding, private val onItemClickLister: OnItemClickLister) :
    BaseViewHolder(binding) {
    fun onBind(poster: Poster) {
        binding.poster = poster
        binding.cardPost.setCardBackgroundColor(Color.parseColor(poster.color))
        binding.iVProfile.setOnClickListener { poster.user?.let { user -> onItemClickLister.onProfileClick(user) } }
        binding.btnWeb.setOnClickListener {
            poster.links?.html?.let { uri ->
                poster.user?.let { user ->
                    onItemClickLister.onWebClick(uri, user)
                }
            }
        }
        poster.user?.profileImage?.medium?.let {
            binding.iVProfile.setImageURI(Uri.parse(it))
        }
        binding.executePendingBindings()
    }
}

interface OnItemClickLister {
    fun onProfileClick(user: User)
    fun onWebClick(uri: String, user: User)
}