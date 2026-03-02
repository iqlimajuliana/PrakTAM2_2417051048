package model
import androidx.annotation.DrawableRes

data class classroom (
    val namaRuang: String,
    val status: String,
    val kapasitas: Int,
    @DrawableRes val imageRes: Int,

    )