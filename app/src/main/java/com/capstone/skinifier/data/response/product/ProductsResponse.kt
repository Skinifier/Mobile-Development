package com.capstone.skinifier.data.response.product

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("list")
    val list: List<ProductDataResponse>
)
