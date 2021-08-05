package ru.nukolay.stupnikov.animelist.ui.filter

import java.io.Serializable

data class Filter(
        val seasons: List<String>,
        val year: String,
        val category: String?,
        val ageRatingList: List<String>
) : Serializable