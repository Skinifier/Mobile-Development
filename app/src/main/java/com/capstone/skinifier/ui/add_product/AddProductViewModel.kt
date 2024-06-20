package com.capstone.skinifier.ui.add_product

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinifier.data.Resource
import com.capstone.skinifier.data.repository.ProductRepository
import com.capstone.skinifier.data.repository.UserRepository
import com.capstone.skinifier.data.request.RequestUploadProduct
import com.capstone.skinifier.util.Utils.getImageByteArrayFromUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddProductViewModel(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _selectedImage = MutableLiveData<ByteArray?>()
    val selectedImage: LiveData<ByteArray?> = _selectedImage

    private val _message = MutableLiveData<String>()
    val message = _message

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess = _isSuccess

    fun setSelectedImage(uri: Uri, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val imageByteArray = getImageByteArrayFromUri(uri, context)
            imageByteArray.also {
                if (it != null) {
                    _selectedImage.postValue(it)
                } else {
                    _message.value = "Pilih Foto Terlebih Dahulu"
                }
            }
        }
    }

    fun uploadProduct(
        nama_brand: String,
        harga: String,
        bahan: String,
        no_hp: String,
        jenis_produk: String,
        nama_barang: String,
        deskripsi: String,
        skin_type: String,
        domisili: String
    ) {
        viewModelScope.launch {
            val register = RequestUploadProduct(
                nama_brand = nama_brand,
                harga = harga,
                no_hp = no_hp,
                bahan = bahan,
                jenis_produk = jenis_produk,
                nama_barang = nama_barang,
                deskripsi = deskripsi,
                skin_type = skin_type,
                domisili = domisili
            )
            userRepository.getSession().collect{
                if (it.token.isNotEmpty()){
                    try {
                        _selectedImage.value?.let {image ->
                            productRepository.uploadProduct(it.token, register, image).collect {
                                when(it) {
                                    is Resource.Error -> {
                                        _message.value = it.error
                                        _isSuccess.value = false
                                    }
                                    Resource.Loading -> {
                                        _message.value = "Uploading"
                                        _isSuccess.value = false
                                    }
                                    is Resource.Success -> {
                                        _message.value = it.data
                                        _isSuccess.value = true
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}