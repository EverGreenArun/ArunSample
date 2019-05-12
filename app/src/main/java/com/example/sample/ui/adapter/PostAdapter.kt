package com.example.sample.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.R
import com.example.sample.base.BaseViewHolder
import com.example.sample.databinding.ViewHolderPostBinding
import com.example.sample.pojo.Post

class PostAdapter(var posts: ArrayList<Post>) : RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(ViewHolderPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(posts[position])
    }
}

class PostViewHolder(private val binding: ViewHolderPostBinding) : BaseViewHolder(binding) {
    fun onBind(post: Post) {
        binding.post = post
        binding.cardPost.setCardBackgroundColor(Color.parseColor(post.color))
        Glide.with(binding.root.context)
            .load(post.user?.profileImage?.medium)
            .placeholder(R.drawable.ic_wb_cloudy)
            .into(binding.iVProfile)
        binding.executePendingBindings()
    }
}