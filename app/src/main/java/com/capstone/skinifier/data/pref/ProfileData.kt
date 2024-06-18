package com.capstone.skinifier.data.pref

import java.io.File

data class ProfileData(
    val email: String,
    val username: String,
    val fullname: String,
    val noHp: String,
    val skinType: String,
    val foto: File? = null
)