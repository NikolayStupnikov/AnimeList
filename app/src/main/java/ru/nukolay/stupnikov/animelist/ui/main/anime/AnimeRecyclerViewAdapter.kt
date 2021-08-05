package ru.nukolay.stupnikov.animelist.ui.main.anime

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nukolay.stupnikov.animelist.data.api.response.anime.AnimeApi
import ru.nukolay.stupnikov.animelist.databinding.ViewHolderAnimeBinding
import ru.nukolay.stupnikov.animelist.ui.base.BaseViewHolder

class AnimeRecyclerViewAdapter(private val animeList: MutableList<AnimeApi>) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val animeViewBinding: ViewHolderAnimeBinding = ViewHolderAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
       return AnimeViewHolder(animeViewBinding)
    }

    fun addItems(list: List<AnimeApi>) {
        animeList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearItems() {
        animeList.clear()
        notifyDataSetChanged()
    }

    inner class AnimeViewHolder(private val binding: ViewHolderAnimeBinding) : BaseViewHolder(binding.getRoot()),
        AnimeItemViewModel.AnimeItemViewModelListener {

        override fun onBind(position: Int) {
            val anime: AnimeApi = animeList[position]
            val animeItemViewModel = AnimeItemViewModel(anime, this)
            binding.viewModel = animeItemViewModel
            binding.executePendingBindings()
        }

        override fun onItemClick() {
            Log.e("Hello", "Успех")
        }
    }
}