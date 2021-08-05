package ru.nukolay.stupnikov.animelist.data.api.response.anime

import com.google.gson.annotations.SerializedName

data class AnimeResponse(
        @SerializedName("data") var result: List<AnimeApi>?,
        @SerializedName("meta") var meta: Meta?
)