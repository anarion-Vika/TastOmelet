package com.requestum.tastomelet.views.dishes

import androidx.recyclerview.widget.DiffUtil

class DishDiffUtil(val dishes : ArrayList<DishEntity>, val newData : ArrayList<DishEntity>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return dishes[oldItemPosition].href == newData[newItemPosition].href
    }

    override fun getOldListSize(): Int {
        return dishes.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return dishes[oldItemPosition].href == newData[newItemPosition].href
                && dishes[oldItemPosition].title == newData[newItemPosition].title
                && dishes[oldItemPosition].ingredients == newData[newItemPosition].ingredients
                && dishes[oldItemPosition].thumbnail == newData[newItemPosition].thumbnail

    }

}