package com.capstone.skinifier.data.mapper

import com.capstone.skinifier.data.model.ProductModelItem
import com.capstone.skinifier.data.response.product.ProductDataResponse

object ProducMapper {
    fun productDRtoModel(q: ProductDataResponse) = ProductModelItem(
        namaBrand = q.namaBrand?: "",
        noHp = q.noHp?: "",
        bahan = q.bahan?:"",
        createdAt = q.createdAt?: "",
        idUser = q.idUser?:"",
        skinType = q.skinType?:"",
        harga = q.harga?:"",
        updatedAt = q.updatedAt?:"",
        jenisProduk = q.jenisProduk?:"",
        namaBarang = q.namaBarang?:"",
        id = q.id?:"",
        deskripsi = q.deskripsi?:"",
        domisili = q.domisili?:"",
        foto = q.foto?:""
    )

}