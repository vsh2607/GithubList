package com.example.realfinalgithubproject.data.model

import com.google.gson.annotations.SerializedName

data class GithubUserDetailResponse(

	@field:SerializedName("gists_url")
	val gistsUrl: String,

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,


	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("id")
	val id: Int,


	@field:SerializedName("email")
	val email: Any,

	@field:SerializedName("organizations_url")
	val organizationsUrl: String,

	@field:SerializedName("hireable")
	val hireable: Any,

	@field:SerializedName("starred_url")
	val starredUrl: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,


	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,



	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,


)
