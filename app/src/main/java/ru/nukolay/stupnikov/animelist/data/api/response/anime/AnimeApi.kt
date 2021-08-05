package ru.nukolay.stupnikov.animelist.data.api.response.anime

import com.google.gson.annotations.SerializedName

data class AnimeApi(@SerializedName("attributes") val attributes: AnimeAttribute?)