package com.example.dao.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.dao.KliDao
import com.example.dao.dataclass.Klient
import com.example.dao.dataclass.KlientAndMark
import com.example.myservices.KliListActivity

class KliRepository(val kliDao: KliDao) {

    suspend fun getKlients(): List<KlientAndMark> {
        val allKliNew: List<KlientAndMark> =
            if (KliListActivity.needCheck) {
                kliDao.getOnlyMark()
            } else {
                kliDao.getKliAndMark()                      //getAll()
            }
        return allKliNew
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(klient: Klient) {
        kliDao.insertAll(klient)
    }
}