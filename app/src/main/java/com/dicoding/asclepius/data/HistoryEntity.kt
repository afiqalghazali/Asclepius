package com.dicoding.asclepius.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "history_database")
@Parcelize
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var imageUri: String? = null,
    var type: String? = null,
    var score: Float? = null,
) : Parcelable
