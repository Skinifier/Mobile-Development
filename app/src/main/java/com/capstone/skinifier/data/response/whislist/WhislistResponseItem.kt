package com.capstone.skinifier.data.response.whislist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WhislistResponseItem(

	@field:SerializedName("id_barang")
	val idBarang: String,

	@field:SerializedName("jumlah_barang")
	val jumlahBarang: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("id_user")
	val idUser: String
) : Parcelable