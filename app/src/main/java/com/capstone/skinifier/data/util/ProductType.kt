package com.capstone.skinifier.data.util

sealed class ProductType(val name: String) {
    data object Moisturizer: ProductType("Moisturizer")
    data object FaceWash: ProductType("FaceWash")
    data object Toner: ProductType("Toner")
    data object Sunscreen: ProductType("Sunscreen")
    data object Serum: ProductType("Serum")
}

