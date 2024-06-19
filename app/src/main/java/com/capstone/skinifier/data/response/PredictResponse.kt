package com.capstone.skinifier.data.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("prediction")
	val prediction: Prediction,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Prediction(

	@field:SerializedName("prediction")
	val prediction: String
)
