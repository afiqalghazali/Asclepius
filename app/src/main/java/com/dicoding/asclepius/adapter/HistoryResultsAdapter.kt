package com.dicoding.asclepius.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.HistoryEntity
import com.dicoding.asclepius.databinding.ItemResultBinding
import java.text.NumberFormat

class HistoryResultsAdapter: ListAdapter<HistoryEntity, HistoryResultsAdapter.ViewHolder>(
    DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: HistoryEntity) {
            binding.tvResultBody.text = result.type
            if (result.type.equals("Cancer", ignoreCase = true)) {
                binding.tvResultBody.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorError))
            } else {
                binding.tvResultBody.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimary))
            }
            binding.tvResultDesc.text = NumberFormat.getPercentInstance().format(result.score).toString()
            binding.ivImage.setImageURI(Uri.parse(result.imageUri))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryEntity>() {
            override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HistoryEntity,
                newItem: HistoryEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}