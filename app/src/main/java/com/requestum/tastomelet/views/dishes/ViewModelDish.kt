package com.requestum.tastomelet.views.dishes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.requestum.tastomelet.App
import com.requestum.tastomelet.data.repository.DishRepositoryServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelDish(application: Application) : AndroidViewModel(application) {
    val dishDao = App.getInstance().database.dishDao()
    private val dishRepository: DishRepositoryServer by lazy {
        DishRepositoryServer(application)
    }
    val dishList: MutableLiveData<List<DishEntity>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()


    init {
        viewModelScope.launch {
            GlobalScope.launch(Dispatchers.IO) {
                val result = dishDao.getAll()
                withContext(Dispatchers.Main) {
                    dishList.value = result
                }
            }
        }
    }

    fun getAllDishesFromServer(query: String) {
        dishRepository.getAllDishes(query, {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    dishList.value = it
                }
            }
        }, { errorMessage.postValue(it) })
    }

}