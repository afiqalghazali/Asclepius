package com.dicoding.asclepius.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.adapter.NewsAdapter
import com.dicoding.asclepius.databinding.ActivityNewsBinding
import com.dicoding.asclepius.network.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = newsAdapter
            addItemDecoration(DividerItemDecoration(this@NewsActivity, LinearLayoutManager.VERTICAL))
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val apiService = ApiConfig.getApiService()
            val response = apiService.getNews(ApiConfig.API_KEY).execute()
            if (response.isSuccessful) {
                val newsResponse = response.body()
                if (newsResponse != null) {
                    val filteredList = newsResponse.articles?.filter { it?.urlToImage != null }
                    withContext(Dispatchers.Main) {
                        newsAdapter.submitList(filteredList)
                    }
                }
            }
            binding.progressBar.visibility = View.GONE
        }

    }
}