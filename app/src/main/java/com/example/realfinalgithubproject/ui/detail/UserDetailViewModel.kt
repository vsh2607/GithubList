package com.example.realfinalgithubproject.ui.detail

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.realfinalgithubproject.api.ApiConfig
import com.example.realfinalgithubproject.data.local.FavoriteUser
import com.example.realfinalgithubproject.data.local.FavoriteUserDao
import com.example.realfinalgithubproject.data.local.UserDatabase
import com.example.realfinalgithubproject.data.model.GithubUserDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application : Application) : AndroidViewModel(application) {

    private val _detailUser = MutableLiveData<GithubUserDetailResponse>()
    val detailUser : LiveData<GithubUserDetailResponse> = _detailUser

    private var userDao : FavoriteUserDao?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }


    fun getUserDetail(username : String, context : Context){
      ApiConfig.getApiService().getUserDetail(username).enqueue(object : Callback<GithubUserDetailResponse>{
          override fun onResponse(
              call: Call<GithubUserDetailResponse>,
              response: Response<GithubUserDetailResponse>
          ) {
              if(response.isSuccessful){
                  _detailUser.postValue(response.body())
              }
          }
          override fun onFailure(call: Call<GithubUserDetailResponse>, t: Throwable) {
              Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
          }

      })
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun addToFavorite(id : Int ,username : String,  avatarUrl : String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(id,username,  avatarUrl)
            userDao?.addToFavorite(user)
        }

    }
    fun removeFromFavorite(id : Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)

        }
    }


}