package com.dicoding.asclepius.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryRepository(private val historyDao: HistoryDao) {

    fun getHistoryResults(): LiveData<List<HistoryEntity>> {
        return historyDao.getResults()
    }

    suspend fun insertHistory(historyEntity: HistoryEntity) {
        withContext(Dispatchers.IO) {
            historyDao.addResults(historyEntity)
        }
    }

    suspend fun deleteAllHistory() {
        withContext(Dispatchers.IO) {
            historyDao.deleteAllResults()
        }
    }
}