package com.capstone.skinifier.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object Utils {
    fun createPartFromString(partString: String): RequestBody {
        return partString.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    fun createImagePart(fileName: String, imageData: ByteArray): MultipartBody.Part {
        val requestBody = imageData.toRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("foto", fileName, requestBody)
    }

    fun getImageByteArrayFromUri(uri: Uri, context: Context): ByteArray? {
        val contentResolver: ContentResolver = context.contentResolver
        var inputStream: InputStream? = null
        try {
            inputStream = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val buffer = ByteArrayOutputStream()
                val byteArray = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(byteArray).also { bytesRead = it } != -1) {
                    buffer.write(byteArray, 0, bytesRead)
                }
                return buffer.toByteArray()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }
}