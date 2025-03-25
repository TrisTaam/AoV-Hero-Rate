package com.tristaam.aovherorate.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "hero",
    foreignKeys = [
        ForeignKey(
            entity = HeroTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["hero_type_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HeroEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "hero_type_id")
    val heroTypeId: String
)