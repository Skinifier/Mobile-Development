package com.capstone.skinifier.data.response.profile

import com.google.gson.annotations.SerializedName

data class ProfileDataResponse(

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

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
