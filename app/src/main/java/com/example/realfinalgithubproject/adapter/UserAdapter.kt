package com.example.realfinalgithubproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.realfinalgithubproject.data.model.GithubUser
import com.example.realfinalgithubproject.databinding.ItemUserBinding

class UserAdapter(val userList : List<GithubUser>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    private  var onItemClickCallBack : OnItemClickCallBack? = null

    fun setOnClickCallBack(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data : GithubUser)
    }


    class ViewHolder(val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user : GithubUser){
            binding.apply{
                Glide.with(itemView).load(user.avatarUrl).into(ivItemUser)
                tvItemUser.text = user.login.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
       holder.itemView.setOnClickListener {
            onItemClickCallBack?.onItemClicked(userList[position])
       }

    }

    override fun getItemCount(): Int = userList.size

}