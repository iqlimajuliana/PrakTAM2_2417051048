package com.example.praktam2_2417051048.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class classroom(
    @SerializedName("nama")
    val namaRuang: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("kapasitas")
    val kapasitas: Int,

    @SerializedName("image_url")
    val imageUrl: String
)