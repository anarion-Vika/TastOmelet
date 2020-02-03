package com.requestum.tastomelet.data.repository

import com.requestum.tastomelet.views.dishes.DishEntity

interface DishRepository {
    fun getAllDishes( query: String,
                   onGet: (MutableList<DishEntity>) -> Unit,
                   onError: (String) -> Unit)
}