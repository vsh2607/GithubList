package com.example.realfinalgithubproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.realfinalgithubproject.data.local.FavoriteUser
import com.example.realfinalgithubproject.databinding.ItemUserBinding

class FavoriteUserAdapter(val favoriteUserList : List<FavoriteUser>) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {


    private  var onItemClickCallBack : OnItemClickCallBack? = null

    fun setOnClickCallBack(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data : FavoriteUser)
    }

    class ViewHolder(val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user : FavoriteUser){
            binding.apply{
                Glide.with(itemView).load(user.avatar_url).into(ivItemUser)
                tvItemUser.text = user.login.toString()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteUserList[position])
        holder.itemView.setOnClickListener {
            onItemClickCallBack?.onItemClicked(favoriteUserList[position])
        }
    }
    override fun getItemCount(): Int = favoriteUserList.size

}