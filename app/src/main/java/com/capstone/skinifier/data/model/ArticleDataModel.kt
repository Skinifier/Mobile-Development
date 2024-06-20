package com.capstone.skinifier.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleDataModel(
    val foto: String,

    val updatedAt: String,

    val createdAt: String,

    val id: String,

    val deskripsi: String,

    val judul: String 
): Parcelable
