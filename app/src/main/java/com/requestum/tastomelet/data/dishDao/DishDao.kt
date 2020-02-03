package com.requestum.tastomelet.data.dishDao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.requestum.tastomelet.views.dishes.DishEntity

@Dao
interface DishDao {
    @Query("SELECT * FROM dishentity")
    fun getAll(): MutableList<DishEntity>

    @Query("DELETE  FROM dishentity ")
    fun delete()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dishEntity: DishEntity)



}