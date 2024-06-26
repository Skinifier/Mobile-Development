package com.capstone.skinifier.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("token")
	val token: String
)

data class User(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String
)
