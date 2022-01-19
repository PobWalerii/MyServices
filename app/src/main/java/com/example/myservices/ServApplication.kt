package com.example.myservices

import android.app.Application
import androidx.room.Room
import com.example.constants.KeyConstants
import com.example.dao.AppDatabase
import com.example.dao.repository.KliRepository
import com.example.dao.repository.SpecRepository

// в манифест обязательно добавить инструкцию  android:name=".ServApplication"

class ServApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            KeyConstants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()  // очищаем при изменениях,
                                                                   // .allowMainThreadQueries() разрешаем работать в основном потоке
    }
    val repository by lazy { KliRepository(database.kliDao()) }
    val repository1 by lazy { SpecRepository(database.specDao()) }

    companion object {
        lateinit var database: AppDatabase
    }

}