package com.capstone.skinifier.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("photo")
	val photo: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("skin_type")
	val skinType: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
