package com.example.dao.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Klient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lastName: String,
    val firstName: String,
    val surName: String,
    val phone: String
)

