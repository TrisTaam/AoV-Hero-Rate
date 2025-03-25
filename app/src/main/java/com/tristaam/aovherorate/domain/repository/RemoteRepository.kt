package com.tristaam.aovherorate.domain.repository

import com.tristaam.aovherorate.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getConfig(): Flow<Result<Unit>>
    fun getServerTrend(): Flow<Result<Unit>>
}