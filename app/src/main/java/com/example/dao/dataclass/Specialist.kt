package com.example.dao.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Specialist(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val lastName: String,
    val firstName: String,
    val surName: String,
    val speciality: String,
    val phone: String,
    val access: Int=0,
    val password: String = ""
)