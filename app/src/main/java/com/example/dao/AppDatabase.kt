package com.example.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dao.dataclass.Klient
import com.example.dao.dataclass.KliMark
import com.example.dao.dataclass.Schedule
import com.example.dao.dataclass.Specialist


@Database(entities = [Klient::class, KliMark::class, Specialist::class, Schedule::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun kliDao(): KliDao
    abstract fun markDao(): MarkDao
    abstract fun specDao(): SpecDao
    abstract fun schedDao(): SchedDao
}