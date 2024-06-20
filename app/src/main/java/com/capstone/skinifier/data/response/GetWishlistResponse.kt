package com.capstone.skinifier.data.response

import com.google.gson.annotations.SerializedName

data class GetWishlistResponseItem(

	@field:SerializedName("id_barang")
	val idBarang: String? = null,

	@field:SerializedName("jumlah_barang")
	val jumlahBarang: Int? = null,

	@field:SerializedName("id")
	val idWishlist: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null
)
