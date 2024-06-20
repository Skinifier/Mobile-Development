package com.capstone.skinifier.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModelItem(

    val namaBrand: String,

    val noHp: String,

    val bahan: String,

    val createdAt: String,

    val idUser: String,

    val skinType: String,

    val harga: String,

    val updatedAt: String,

    val jenisProduk: String,

    val namaBarang: String,

    val id: String,

    val deskripsi: String,

    val domisili: String,

    val foto: String
): Parcelable
