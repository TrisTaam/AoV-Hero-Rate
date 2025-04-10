package com.tristaam.aovherorate.domain.repository

import com.tristaam.aovherorate.domain.model.Server
import kotlinx.coroutines.flow.Flow

interface ServerRepository {
    fun getAllServers(): Flow<List<Server>>
}