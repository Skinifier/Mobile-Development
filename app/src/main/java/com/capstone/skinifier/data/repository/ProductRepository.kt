package com.capstone.skinifier.data.repository

import android.util.Log
import com.capstone.skinifier.data.Resource
import com.capstone.skinifier.data.api.NewApiConfig
import com.capstone.skinifier.data.mapper.ProducMapper.productDRtoModel
import com.capstone.skinifier.data.model.ProductModelItem
import com.capstone.skinifier.data.request.RequestUploadProduct
import com.capstone.skinifier.util.Utils.createImagePart
import com.capstone.skinifier.util.Utils.createPartFromString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository {

    private val apiService = NewApiConfig.getProductApiService()

    suspend fun uploadProduct(token: String, request: RequestUploadProduct, image: ByteArray): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        emit(
            try {
                val jwtToken = "Bearer $token"
                // Example data
                val namaBrand = createPartFromString(request.nama_brand)
                val harga = createPartFromString(request.harga)
                val noHp = createPartFromString(request.no_hp)
                val bahan = createPartFromString(request.bahan)
                val jenisProduk = createPartFromString(request.jenis_produk)
                val namaBarang = createPartFromString(request.nama_barang)
                val deskripsi = createPartFromString(request.deskripsi)
                val skinType = createPartFromString(request.skin_type)
                val domisili = createPartFromString(request.domisili)
                val foto = createImagePart("image.jpg", image)

                apiService.uploadProduct(
                    token = jwtToken,
                    namaBrand = namaBrand,
                    harga = harga,
                    noHp = noHp,
                    foto = foto,
                    bahan = bahan,
                    jenisProduk = jenisProduk,
                    namaBarang = namaBarang,
                    deskripsi = deskripsi,
                    skinType = skinType,
                    domisili = domisili
                )
                Resource.Success("Sukess")
            }catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        )
    }

    suspend fun getAllProducts(token : String, skinType: String = "", brandName: String = ""): List<ProductModelItem> {
        try {
            val jwtToken = "Bearer $token"
            val response = apiService.getAllProducts(
                token = jwtToken, skinType = skinType, brandName = brandName,
                
            ).map {
                productDRtoModel(it)
            }
            return response
        } catch (e: Exception) {
            Log.e("PR", e.message.toString())
        }

        return listOf()
    }
}