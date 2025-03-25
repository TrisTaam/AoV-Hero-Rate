package com.tristaam.aovherorate.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_mode")
data class GameModeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "image_active")
    val imageActive: String,

    @ColumnInfo(name = "has_rank")
    val hasRank: Boolean
)