package ru.nukolay.stupnikov.animelist.data.api.response.detail

import com.google.gson.annotations.SerializedName

data class DetailApi(
    @SerializedName("attributes") val attributes: DetailAttribute?,
)