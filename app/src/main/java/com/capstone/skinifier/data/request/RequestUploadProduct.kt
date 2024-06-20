package com.capstone.skinifier.data.request

data class RequestUploadProduct(
    val nama_brand: String,
    val harga: String,
    val no_hp: String,
    val bahan: String,
    val jenis_produk: String,
    val nama_barang: String,
    val deskripsi: String,
    val skin_type: String,
    val domisili: String
)