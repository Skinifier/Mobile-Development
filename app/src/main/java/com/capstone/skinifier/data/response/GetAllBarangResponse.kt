package com.capstone.skinifier.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GetAllBarangResponseItem(

	@field:SerializedName("nama_brand")
	val namaBrand: String? = null,

	@field:SerializedName("no_hp")
	val noHp: String? = null,

	@field:SerializedName("bahan")
	val bahan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("skin_type")
	val skinType: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("jenis_produk")
	val jenisProduk: String? = null,

	@field:SerializedName("nama_barang")
	val namaBarang: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("foto")
	val foto1: String? = null,

	@field:SerializedName("domisili")
	val domisili: String? = null,

	@field:SerializedName("foto2")
	val foto2: String? = null,

	@field:SerializedName("foto1")
	val foto: String? = null
) : Parcelable


