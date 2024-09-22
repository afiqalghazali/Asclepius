package com.dicoding.asclepius.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.adapter.HistoryResultsAdapter
import com.dicoding.asclepius.data.HistoryDatabase
import com.dicoding.asclepius.data.HistoryEntity
import com.dicoding.asclepius.data.HistoryRepository
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryResultsAdapter
    private lateinit var historyRepository: HistoryRepository
    private lateinit var historyList: LiveData<List<HistoryEntity>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyAdapter = HistoryResultsAdapter()
        binding.rvHistory.adapter = historyAdapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvHistory.layoutManager = layoutManager

        val database = HistoryDatabase.getInstance(applicationContext)
        historyRepository = HistoryRepository(database.historyDao())
        historyList = historyRepository.getHistoryResults()

        historyList.observe(this) { history ->
            historyAdapter.submitList(history)
        }

        binding.deleteButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                historyRepository.deleteAllHistory()
            }
        }
    }

}