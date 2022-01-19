package com.example.dao

import androidx.room.*
import com.example.dao.dataclass.Klient
import com.example.dao.dataclass.KlientAndMark
import kotlinx.coroutines.flow.Flow

@Dao
interface KliDao {

    @Query("SELECT * FROM klient ORDER BY lastName ASC")
    suspend fun getAll(): List<Klient>

    @Transaction
    @Query("SELECT * FROM klient ORDER BY lastName ASC")
    suspend fun getKliAndMark(): List<KlientAndMark>

    @Transaction
    @Query("SELECT * FROM klient JOIN klimark ON klient.id=klimark.idKli WHERE klimark.mark=1 ORDER BY lastName ASC")
    suspend fun getOnlyMark(): List<KlientAndMark>

    @Transaction
    @Query("SELECT * FROM klient WHERE id IN (:curId)")
    suspend fun getByIDKliAndMark(curId: IntArray): List<KlientAndMark>


    @Query("SELECT * FROM klient WHERE id IN (:curId)")
    suspend fun getByID(curId: IntArray): List<Klient>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(klient: Klient): Long


    @Delete
    suspend fun delete(user: Klient)
}