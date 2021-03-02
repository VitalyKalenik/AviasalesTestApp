package com.vitalykalenik.aviatest.view.recycler

import androidx.recyclerview.widget.DiffUtil
import com.vitalykalenik.aviatest.models.City

/**
 * Реализация [DiffUtil.Callback] для адаптера на экране с поисковой строкой
 *
 * @property oldList Список городов до обновления
 * @property newList Список городов после обновления
 *
 * @author Vitaly Kalenik
 */
class SearchDiffUtil(
    private val oldList: List<City>,
    private val newList: List<City>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}