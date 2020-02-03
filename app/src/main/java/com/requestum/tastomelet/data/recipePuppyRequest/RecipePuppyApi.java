package com.requestum.tastomelet.data.recipePuppyRequest;

import com.requestum.tastomelet.views.dishes.DishEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RecipePuppyApi {

    @GET("api/")
    Call<DishEntity.DishEntityResponse> getDish(@Query("q") String searchDish);

}
