package com.capstone.skinifier.data.model

import com.google.gson.annotations.SerializedName

data class ProfileDataModel(
    val noHp: String,
    val photo: String,
    val id: String,
    val fullname: String,
    val skinType: String,
    val email: String,
    val username: String
)
