package com.example.realfinalgithubproject.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realfinalgithubproject.adapter.FavoriteUserAdapter
import com.example.realfinalgithubproject.data.local.FavoriteUser
import com.example.realfinalgithubproject.databinding.ActivityFavoriteBinding
import com.example.realfinalgithubproject.ui.detail.UserDetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter : FavoriteUserAdapter
    private lateinit var viewModel : FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        viewModel.getFavoriteUser()?.observe(this) {
            showFavoriteUser(it)
        }
    }

    private fun showFavoriteUser(userList : List<FavoriteUser>){
        adapter = FavoriteUserAdapter(userList)
        binding.rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        showLoading(false)
        binding.rvUser.adapter = adapter
        adapter.setOnClickCallBack(object : FavoriteUserAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: FavoriteUser) {
                val intent = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(UserDetailActivity.EXTRA_URL, data.avatar_url)
                intent.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }

        })
    }


    private fun showLoading(state : Boolean){
        if(state){
            binding.pbProgressBar.visibility = View.VISIBLE
        }else{
            binding.pbProgressBar.visibility = View.GONE
        }
    }

}