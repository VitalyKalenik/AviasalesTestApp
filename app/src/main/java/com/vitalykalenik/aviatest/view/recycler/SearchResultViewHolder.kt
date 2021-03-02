package com.vitalykalenik.aviatest.view.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vitalykalenik.aviatest.R
import com.vitalykalenik.aviatest.models.City
import com.vitalykalenik.aviatest.view.activity.SearchActivity

/**
 * Вьюхолдер элемента для списка на экране со строкой поиска
 *
 * @constructor
 * @param Вью элемента
 *
 * @author Vitaly Kalenik
 */
class SearchResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.search_result_city)

    fun bindView(city: City, onClick: SearchActivity.SearchResultClick) {
        title.text = city.fullname
        itemView.setOnClickListener { onClick.click(city) }
    }
}