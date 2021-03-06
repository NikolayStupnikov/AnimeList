package ru.nukolay.stupnikov.animelist.data.api.response.category

import com.google.gson.annotations.SerializedName

data class CategoryApi(
    @SerializedName("attributes") val attributes: CategoryAttribute?,
    @SerializedName("id") val id: Int
)