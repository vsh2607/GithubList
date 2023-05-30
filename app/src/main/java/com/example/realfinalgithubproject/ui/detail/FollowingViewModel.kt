package com.example.realfinalgithubproject.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realfinalgithubproject.api.ApiConfig
import com.example.realfinalgithubproject.data.model.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val _listFollowing = MutableLiveData<List<GithubUser>>()
    val listFollowing  : LiveData<List<GithubUser>> = _listFollowing

    fun getFollowing(username : String){
        ApiConfig.getApiService().getFollowing(username).enqueue(object : Callback<List<GithubUser>>{
            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                    if(response.isSuccessful){
                        _listFollowing.postValue(response.body())
                    }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                Log.d("TAG", "Getting Following failed")

            }

        })
    }

}