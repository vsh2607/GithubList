package com.example.realfinalgithubproject.data.model

import com.google.gson.annotations.SerializedName

data class GithubUserListResponse(

	@field:SerializedName("items")
	val items: List<GithubUser>
)

data class GithubUser(


	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,



)
