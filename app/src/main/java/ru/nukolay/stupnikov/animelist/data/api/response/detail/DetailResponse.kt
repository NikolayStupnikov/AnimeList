package ru.nukolay.stupnikov.animelist.data.api.response.detail

import com.google.gson.annotations.SerializedName

data class DetailResponse(
        @SerializedName("data") var result: DetailApi?
)