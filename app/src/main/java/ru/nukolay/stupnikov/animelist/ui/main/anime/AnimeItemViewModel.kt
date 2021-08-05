package ru.nukolay.stupnikov.animelist.ui.main.anime

import androidx.databinding.ObservableField
import ru.nukolay.stupnikov.animelist.data.api.response.anime.AnimeApi
import ru.nukolay.stupnikov.animelist.data.api.response.anime.Titles

class AnimeItemViewModel(
        private val anime: AnimeApi,
        private val listener: AnimeItemViewModelListener
) {

    var imageUrl: ObservableField<String> = ObservableField(anime.attributes?.posterImage?.original)
    var name: ObservableField<Titles> = ObservableField(anime.attributes?.titles)

    fun onItemClick() {
        listener.onItemClick()
    }

    interface AnimeItemViewModelListener {
        fun onItemClick()
    }

}