package com.example.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dao.dataclass.Klient
import com.example.dao.dataclass.Specialist
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecDao {
    @Query("SELECT * FROM Specialist ORDER BY speciality,lastName ASC")
    suspend fun getAll(): List<Specialist>

    @Query("SELECT * FROM Specialist WHERE id IN (:curId)")
    suspend fun getByID(curId: IntArray): List<Specialist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(specialist: Specialist): Long

    @Delete
    suspend fun delete(user: Specialist)
}
