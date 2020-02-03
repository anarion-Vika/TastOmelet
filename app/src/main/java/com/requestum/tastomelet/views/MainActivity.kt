package com.requestum.tastomelet.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.requestum.tastomelet.R
import com.requestum.tastomelet.views.dishes.DishFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentContainer,
                DishFragment(), "DishFragment")
            .commit()
    }
}
