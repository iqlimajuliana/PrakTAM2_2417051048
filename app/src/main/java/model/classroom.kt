package model
import com.google.gson.annotations.SerializedName

data class classroom (
    @SerializedName("nama_ruang")
    val namaRuang: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("kapasitas")
    val kapasitas: Int,
    @SerializedName("image_url")
    val imageRes: String,

    )