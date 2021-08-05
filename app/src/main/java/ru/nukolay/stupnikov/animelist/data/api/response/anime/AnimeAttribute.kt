package ru.nukolay.stupnikov.animelist.data.api.response.anime

import com.google.gson.annotations.SerializedName

data class AnimeAttribute(
        @SerializedName("titles") val titles: Titles?,
        @SerializedName("posterImage") val posterImage: Poster?
)