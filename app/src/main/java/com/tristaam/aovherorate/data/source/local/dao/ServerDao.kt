package com.tristaam.aovherorate.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.tristaam.aovherorate.data.source.local.entity.ServerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServerDao {
    @Query("SELECT * FROM server")
    fun getAllServerEntities(): Flow<List<ServerEntity>>
}