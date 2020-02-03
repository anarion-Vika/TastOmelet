package com.requestum.tastomelet.views.dishes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class DishEntity {
    @SerializedName("title")
    var title: String = ""
    @SerializedName("ingredients")
    var ingredients: String = ""
    @PrimaryKey(autoGenerate = false)
    @SerializedName("href")
    var href: String = ""
    @SerializedName("thumbnail")
    var thumbnail: String = ""


    constructor(title: String, ingredients: String, href: String, thumbnail: String) {
        this.title = title
        this.ingredients = ingredients
        this.href = href
        this.thumbnail = thumbnail
    }

    class DishEntityResponse (@SerializedName("results")
                              var resultList:List<DishEntity>)
}