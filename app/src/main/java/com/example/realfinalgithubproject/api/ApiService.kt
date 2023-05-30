package com.example.realfinalgithubproject.api


import com.example.realfinalgithubproject.data.model.GithubUser
import com.example.realfinalgithubproject.data.model.GithubUserDetailResponse
import com.example.realfinalgithubproject.data.model.GithubUserListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface ApiService {


    @GET("search/users")
    fun searchUsers(@Query("q") username : String) : Call<GithubUserListResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username : String) : Call<GithubUserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username : String ) : Call<List<GithubUser>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username : String ) : Call<List<GithubUser>>


}