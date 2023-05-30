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

class FollowersViewModel : ViewModel() {

    private val _listFollowers = MutableLiveData<List<GithubUser>>()
    val listFollowers : LiveData<List<GithubUser>> = _listFollowers

    fun getFollowers(username : String){
        ApiConfig.getApiService().getFollowers(username).enqueue(object : Callback<List<GithubUser>>{
            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                if(response.isSuccessful){
                    _listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                Log.d("TAG", "Getting Followers Failed")
            }

        })
    }

}