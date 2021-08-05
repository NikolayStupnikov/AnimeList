package ru.nukolay.stupnikov.animelist.data.api.response.category

import com.google.gson.annotations.SerializedName
import ru.nukolay.stupnikov.animelist.data.api.response.anime.Meta

data class CategoryResponse(
        @SerializedName("data") var result: List<CategoryApi>?,
        @SerializedName("meta") var meta: Meta?
)