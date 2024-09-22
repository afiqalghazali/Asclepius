package com.dicoding.asclepius.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.HistoryDatabase
import com.dicoding.asclepius.data.HistoryEntity
import com.dicoding.asclepius.data.HistoryRepository
import kotlinx.coroutines.launch

class ResultViewModel(application: Application): ViewModel() {

    private val repository: HistoryRepository

    init {
        val database = HistoryDatabase.getInstance(application)
        repository = HistoryRepository(database.historyDao())
    }

    fun insertHistory(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            repository.insertHistory(historyEntity)
        }
    }
}