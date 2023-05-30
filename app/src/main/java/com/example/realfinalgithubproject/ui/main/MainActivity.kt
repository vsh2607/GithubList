package com.example.realfinalgithubproject.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realfinalgithubproject.R
import com.example.realfinalgithubproject.adapter.UserAdapter
import com.example.realfinalgithubproject.data.model.GithubUser
import com.example.realfinalgithubproject.databinding.ActivityMainBinding
import com.example.realfinalgithubproject.ui.detail.UserDetailActivity
import com.example.realfinalgithubproject.ui.favorite.FavoriteActivity
import com.example.realfinalgithubproject.ui.theme.SettingPreferences
import com.example.realfinalgithubproject.ui.theme.ThemeActivity
import com.example.realfinalgithubproject.ui.theme.ThemeViewModel
import com.example.realfinalgithubproject.ui.theme.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var adapter : UserAdapter


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    override fun onCreate(savedInstanceState: Bundle?) {

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ThemeViewModel::class.java]
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.btnSearch.setBackgroundResource(R.drawable.ic_search_white)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.btnSearch.setBackgroundResource(R.drawable.ic_search_black)
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener{
            searchUser()
        }
        binding.etSearch.setOnKeyListener{ _, keyCode, event ->
                if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    Log.d("TAG", "event Clicked")
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.githubUser.observe(this) {
            if(it.isEmpty()){
                showLoading(false)
                binding.tvPageInfo.visibility = View.VISIBLE
                binding.rvUser.visibility = View.GONE
                binding.tvPageInfo.text = resources.getString(R.string.no_result)
            }else{
                binding.rvUser.visibility = View.VISIBLE
                getGithubUserList(it)
            }
        }

    }

    private fun searchUser(){
        val searchResult = binding.etSearch.text.toString()
        if(searchResult.isEmpty()){
            return
        }else{
            showLoading(true)
            binding.tvPageInfo.visibility = View.GONE
            viewModel.searchUsers(searchResult, this@MainActivity)
        }
    }

    private fun getGithubUserList(userList : List<GithubUser>){
        adapter = UserAdapter(userList)
        binding.rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
        showLoading(false)
        binding.rvUser.adapter = adapter
        adapter.setOnClickCallBack(object : UserAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: GithubUser) {
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(UserDetailActivity.EXTRA_URL, data.avatarUrl)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.settings_menu -> {
                val intent = Intent(this, ThemeActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}