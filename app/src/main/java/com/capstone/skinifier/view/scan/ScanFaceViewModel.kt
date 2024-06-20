package com.capstone.skinifier.view.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.pref.PredictData
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.response.PredictResponse
import kotlinx.coroutines.launch

class ScanFaceViewModel(private val repository: UserRepository) : ViewModel() {

    private val _predictionResponse = MutableLiveData<PredictResponse>()
    val predictionResponse: LiveData<PredictResponse> = _predictionResponse

    fun submitImage(predictData: PredictData) {
        viewModelScope.launch {
            try {
                val response = repository.scanFace(predictData)
                if (response.message == "Prediction Success") {
                    _predictionResponse.value = response
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

}