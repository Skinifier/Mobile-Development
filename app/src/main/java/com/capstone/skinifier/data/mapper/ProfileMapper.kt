package com.capstone.skinifier.data.mapper

import com.capstone.skinifier.data.model.ProfileDataModel
import com.capstone.skinifier.data.response.profile.ProfileDataResponse

object ProfileMapper {
    fun mapProfileResponseToModel(q: ProfileDataResponse) = ProfileDataModel(
        noHp = q.noHp ?: "",
        photo = q.photo ?: "",
        id = q.id ?: "",
        fullname = q.fullname ?: "",
        skinType = q.skinType ?: "",
        email = q.email ?: "",
        username = q.username ?: ""
    )
}