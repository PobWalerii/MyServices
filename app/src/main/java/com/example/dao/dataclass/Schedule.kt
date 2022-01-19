package com.example.dao.dataclass

import androidx.room.Entity

@Entity(primaryKeys = ["specId", "date","time"])
data class Schedule(
    val specId: Int,
    val date: String,
    val time: String,
    val idKlient: Int
)