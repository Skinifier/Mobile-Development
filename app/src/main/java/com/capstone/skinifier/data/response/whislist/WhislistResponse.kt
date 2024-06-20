package com.capstone.skinifier.data.response.whislist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WhislistResponse(

	@field:SerializedName("WhislistResponse")
	val whislistResponse: List<WhislistResponseItem>
) : Parcelable