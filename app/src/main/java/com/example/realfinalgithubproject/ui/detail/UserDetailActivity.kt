package com.example.realfinalgithubproject.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.realfinalgithubproject.R
import com.example.realfinalgithubproject.adapter.SectionPagerAdapter
import com.example.realfinalgithubproject.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserDetailBinding
    private lateinit var viewModel : UserDetailViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.Followers,
            R.string.Following
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)


        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]
        viewModel.getUserDetail(username!!, this@UserDetailActivity)
        viewModel.detailUser.observe(this) {
            if(it != null){
                Glide.with(this@UserDetailActivity).load(it.avatarUrl).into(binding.ivProfileUser)
                binding.tvUsername.text = it.login.toString()
                binding.tvName.text = it?.name?.toString()
                binding.tvFollowing.text = "${it.following} Following"
                binding.tvFollowers.text = "${it.followers} Followers"
            }
        }

        var _isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count != null){
                    if(count > 0){
                        setBtnFavoriteColor(R.drawable.ic_favorite_grey)
                        _isFavorite = true
                    }else{
                        setBtnFavoriteColor(R.drawable.ic_favorite_border)
                        _isFavorite = false
                    }
                }
            }
        }

        binding.btnLike.setOnClickListener{
            _isFavorite = !_isFavorite
            if(_isFavorite){
                setBtnFavoriteColor(R.drawable.ic_favorite_grey)
                Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT).show()
                viewModel.addToFavorite(id, username, avatarUrl!!)
            }
            else{
                setBtnFavoriteColor(R.drawable.ic_favorite_border)
                Toast.makeText(this, "Unfavorited", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "Not Favorite")
                viewModel.removeFromFavorite(id)
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this@UserDetailActivity, bundle)
        var viewPager : ViewPager2 = binding.viewPager
        val tabs : TabLayout = binding.tabs
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(tabs, viewPager){
            tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    fun setBtnFavoriteColor(color : Int){
        binding.btnLike.setImageDrawable(resources.getDrawable(color))
    }
}