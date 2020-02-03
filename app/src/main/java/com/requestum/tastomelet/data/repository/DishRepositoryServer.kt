package com.requestum.tastomelet.data.repository

import android.os.Handler
import android.os.Looper
import com.requestum.tastomelet.data.recipePuppyRequest.NetworkService
import com.requestum.tastomelet.views.dishes.DishEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context
import com.requestum.tastomelet.App
import com.requestum.tastomelet.R
import com.requestum.tastomelet.data.dishDao.DishDao
import com.requestum.tastomelet.utils.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DishRepositoryServer(var context: Context) : DishRepository {
    val dishDao: DishDao by lazy {
        App.getInstance().database.dishDao()
    }

    private var networkUtil: NetworkUtil = NetworkUtil(context)

    override fun getAllDishes(
        query: String,
        onGet: (MutableList<DishEntity>) -> Unit,
        onError: (String) -> Unit
    ) {
        if (!networkUtil.checkNetworkAvailable()) {
            GlobalScope.launch(Dispatchers.IO) {
                onGet.invoke(dishDao.getAll())
            }
        } else {
            getDishesResponse(query, { onGet.invoke(it) }, { onError.invoke(it) })
        }
    }

    private fun getDishesResponse(
        query: String,
        onGet: (MutableList<DishEntity>) -> Unit,
        onError: (String) -> Unit
    ) {
        NetworkService.recipePuppyApi.getDish(query).enqueue(
            object : Callback<DishEntity.DishEntityResponse> {
                var handler = Handler(Looper.getMainLooper())
                override fun onFailure(call: Call<DishEntity.DishEntityResponse>, t: Throwable) {
                    handler.post {
                        onError.invoke(
                            t.message ?: context.getString(R.string.error_response)
                        )
                    }
                }

                override fun onResponse(
                    call: Call<DishEntity.DishEntityResponse>,
                    response: Response<DishEntity.DishEntityResponse>
                ) {
                    handler.post {
                        if (response.isSuccessful) {
                            response.body()?.resultList?.let {
                                if (it.isEmpty())
                                    GlobalScope.launch(Dispatchers.IO) {
                                        dishDao.delete()
                                        onError.invoke(context.getString(R.string.erro_result_absent))
                                    }
                                else {
                                    GlobalScope.launch(Dispatchers.IO) {
                                        dishDao.delete()
                                        for (item: DishEntity in it) {
                                            dishDao.insert(item)
                                        }
                                        onGet.invoke(dishDao.getAll())
                                    }
                                }
                            }
                        } else {
                            onError.invoke(context.getString(R.string.error_response))
                        }
                    }
                }
            }
        )
    }

}

