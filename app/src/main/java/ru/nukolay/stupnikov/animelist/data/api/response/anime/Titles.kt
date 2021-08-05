package ru.nukolay.stupnikov.animelist.data.api.response.anime

import com.google.gson.annotations.SerializedName

data class Titles(
    @SerializedName("en") val en: String?,
    @SerializedName("en_jp") val enJp: String?,
    @SerializedName("ja_jp") val jp: String?
)