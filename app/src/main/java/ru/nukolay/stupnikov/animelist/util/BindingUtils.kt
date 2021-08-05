package ru.nukolay.stupnikov.animelist.util

import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.nukolay.stupnikov.animelist.R
import ru.nukolay.stupnikov.animelist.data.api.response.anime.AnimeApi
import ru.nukolay.stupnikov.animelist.data.api.response.anime.Titles
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryApi
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryAttribute
import ru.nukolay.stupnikov.animelist.ui.filter.category.CategoryListAdapter
import ru.nukolay.stupnikov.animelist.ui.main.anime.AnimeRecyclerViewAdapter

object BindingUtils {

    @JvmStatic
    @BindingAdapter("adapter")
    fun addAnimeItems(recyclerView: RecyclerView, list: List<AnimeApi>) {
        (recyclerView.adapter as? AnimeRecyclerViewAdapter)?.let {
            it.clearItems()
            it.addItems(list)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        if (url != null) {
            Picasso.get()
                .load(url)
                .resize(
                    imageView.context.resources.getDimension(R.dimen.logo_size).toInt(),
                    imageView.context.resources.getDimension(R.dimen.logo_size).toInt()
                )
                .transform(RoundedCornersTransformation(
                    imageView.context.resources.getDimension(R.dimen.radius_logo).toInt(),
                    0)
                )
                .centerCrop()
                .placeholder(R.drawable.icon_logo)
                .error(R.drawable.icon_logo)
                .into(imageView)
        } else {
            Picasso.get().load(R.drawable.icon_logo).into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("title")
    fun setTitle(textView: TextView, titles: Titles?) {
        if (titles != null) {
            if (!titles.en.isNullOrEmpty()) {
                textView.text = titles.en
            } else if (!titles.enJp.isNullOrEmpty()) {
                textView.text = titles.enJp
            } else if (!titles.jp.isNullOrEmpty()) {
                textView.text = titles.jp
            } else {
                textView.text = textView.context.getString(R.string.empty)
            }
        } else {
            textView.text = textView.context.getString(R.string.empty)
        }
    }

    @JvmStatic
    @BindingAdapter("categories", "selectCategory")
    fun setCategory(spinner: Spinner, categories: List<CategoryApi>, selectCategory: String?) {
        (spinner.adapter as? CategoryListAdapter)?.let {
            it.clear()
            it.add(CategoryApi(CategoryAttribute(
                    spinner.context.getString(R.string.all_categories),
                    spinner.context.getString(R.string.all_categories)), 0))
            it.addAll(categories)
            if (!selectCategory.isNullOrEmpty() && categories.isNotEmpty()) {
                val categoryApi: CategoryApi? =
                    categories.firstOrNull { category -> category.attributes?.slug == selectCategory }
                if (categoryApi != null) {
                    spinner.setSelection(categories.indexOf(categoryApi) + 1)
                }
            }
        }
    }
}