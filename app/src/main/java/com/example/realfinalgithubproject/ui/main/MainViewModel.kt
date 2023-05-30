package com.example.realfinalgithubproject.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realfinalgithubproject.api.ApiConfig
import com.example.realfinalgithubproject.data.model.GithubUser
import com.example.realfinalgithubproject.data.model.GithubUserListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

        private val _githubUser = MutableLiveData<List<GithubUser>>()
        val githubUser : LiveData<List<GithubUser>> =_githubUser

        fun searchUsers(username : String, context : Context){
            ApiConfig.getApiService().searchUsers(username)
                .enqueue(object : Callback<GithubUserListResponse>{
                    override fun onResponse(
                        call: Call<GithubUserListResponse>,
                        response: Response<GithubUserListResponse>
                    ) {
                        if(response.isSuccessful){
                            _githubUser.postValue(response.body()?.items)
                        }
                    }

                    override fun onFailure(call: Call<GithubUserListResponse>, t: Throwable) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }

                })
        }
}