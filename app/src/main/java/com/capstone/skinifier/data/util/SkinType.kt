package com.capstone.skinifier.data.util

sealed class SkinType(val skinType: String) {
    object OilySkin: SkinType("Berminyak")
    object DrySkin: SkinType("Kering")
    object NormalSkin: SkinType("Normal")
    object AcneSkin: SkinType("Jerawat")
}