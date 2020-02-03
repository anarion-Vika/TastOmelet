package com.requestum.tastomelet

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.requestum.tastomelet.data.database.AppDatabase

class App : Application() {

    lateinit var database: AppDatabase

    companion object {
        private lateinit var instance: App
        fun getInstance(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "recipe-lab.db"
        ).build()
    }
}