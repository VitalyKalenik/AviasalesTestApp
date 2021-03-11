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
 * @param onClick Коллбэк нажатия
 *
 * @author Vitaly Kalenik
 */
class SearchResultViewHolder(view: View, onClick: SearchActivity.SearchResultClick) : RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.search_result_city)
    private lateinit var currentCity: City

    init {
        itemView.setOnClickListener {
            if (::currentCity.isInitialized) {
                onClick.click(currentCity)
            }
        }
    }

    fun bindView(city: City) {
        title.text = city.fullname
        currentCity = city
    }
}