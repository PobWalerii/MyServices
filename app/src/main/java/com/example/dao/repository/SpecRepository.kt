package com.example.dao.repository

import androidx.annotation.WorkerThread
import com.example.dao.SpecDao
import com.example.dao.dataclass.Specialist

class SpecRepository(val specDao: SpecDao) {


    suspend fun getSpecialists(): List<Specialist> {
        val allSpec: List<Specialist> = specDao.getAll()
        return allSpec
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(specialist: Specialist) {
        specDao.insertAll(specialist)
    }
}