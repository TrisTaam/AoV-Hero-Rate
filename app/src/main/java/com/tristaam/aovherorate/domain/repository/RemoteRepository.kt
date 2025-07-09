package com.tristaam.aovherorate.domain.repository

import com.tristaam.aovherorate.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getRemoteData(): Flow<Result<Unit>>
}