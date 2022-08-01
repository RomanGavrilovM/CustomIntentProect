package ru.example.customintentproect.data.retrofit

import ru.example.customintentproect.domain.DogEntity
import com.google.gson.annotations.SerializedName

data class DogDto(
    @SerializedName("message")
    var message: String? = null,

    @SerializedName("status")
    var status: String? = null

) {
    fun convertDtoToEntity() = DogEntity (message,status)
}