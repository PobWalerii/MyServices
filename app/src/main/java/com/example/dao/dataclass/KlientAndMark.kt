package com.example.dao.dataclass

import androidx.room.Embedded
import androidx.room.Relation

data class KlientAndMark(
    @Embedded val klient: Klient,
    @Relation(
        parentColumn = "id",
        entityColumn = "idKli"
    )
    val klimark: KliMark?
)