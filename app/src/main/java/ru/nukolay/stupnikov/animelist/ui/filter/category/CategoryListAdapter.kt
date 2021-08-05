package ru.nukolay.stupnikov.animelist.ui.filter.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.nukolay.stupnikov.animelist.R
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryApi

open class CategoryListAdapter(context: Context, val list: List<CategoryApi>) :
    ArrayAdapter<CategoryApi>(context, R.layout.item_spinner_theme, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val resultView =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.item_spinner_theme, null)
        val textView = resultView.findViewById<TextView>(R.id.text)
        textView.text = item?.attributes?.title ?: item?.attributes?.slug ?: ""
        return resultView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val resultView =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.item_spinner_theme_dropdown, null)
        val textView = resultView.findViewById<TextView>(R.id.text)
        textView.text = item?.attributes?.title ?: item?.attributes?.slug ?: ""
        return resultView
    }
}