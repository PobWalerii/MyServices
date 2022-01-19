package com.example.dao.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KliMark(
    @PrimaryKey val idKli: Int,
    val mark: Int=0
)