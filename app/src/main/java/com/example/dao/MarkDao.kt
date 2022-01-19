package com.example.dao

import androidx.room.*
import com.example.dao.dataclass.KliMark
import com.example.dao.dataclass.Klient

@Dao
interface MarkDao {
    //@Query("SELECT * FROM klimark")
    //suspend fun getAll(): List<KliMark>

    @Query("SELECT * FROM klimark WHERE idKli IN (:curId)")
    suspend fun getByID(curId: IntArray): List<KliMark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(klimark: KliMark): Long

    @Delete
    suspend fun delete(user: KliMark)

    @Query("DELETE FROM klimark")
    suspend fun deleteAll()

}



