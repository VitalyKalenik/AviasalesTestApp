package com.vitalykalenik.aviatest.view.recycler

import androidx.recyclerview.widget.DiffUtil
import com.vitalykalenik.aviatest.view.models.CityModel

/**
 * Реализация [DiffUtil.Callback] для адаптера на экране с поисковой строкой
 *
 * @property oldList Список городов до обновления
 * @property newList Список городов после обновления
 *
 * @author Vitaly Kalenik
 */
class SearchDiffUtil(
    private val oldList: List<CityModel>,
    private val newList: List<CityModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].city == newList[newItemPosition].city

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}