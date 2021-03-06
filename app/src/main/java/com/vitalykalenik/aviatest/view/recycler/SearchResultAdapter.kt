package com.vitalykalenik.aviatest.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vitalykalenik.aviatest.R
import com.vitalykalenik.aviatest.domain.models.City
import com.vitalykalenik.aviatest.view.activity.SearchActivity
import com.vitalykalenik.aviatest.view.models.CityModel

/**
 * Адаптер для списка на экране со строкой поиска
 *
 * @property clickListener Коллбэк нажатия на элемент списка
 *
 * @author Vitaly Kalenik
 */
class SearchResultAdapter(private val clickListener: SearchActivity.SearchResultClick) :
    RecyclerView.Adapter<SearchResultViewHolder>() {

    private val itemList = mutableListOf<CityModel>()

    /**
     * Обновить список
     *
     * @param newList Новый список городов
     */
    fun updateList(newList: List<CityModel>) {
        val diffUtil = SearchDiffUtil(itemList, newList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        itemList.clear()
        itemList.addAll(newList)
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder = SearchResultViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_search_result_item, parent, false),
        clickListener
    )

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) =
        holder.bindView(itemList[position])
}