package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toServer
import com.tristaam.aovherorate.data.source.local.dao.ServerDao
import com.tristaam.aovherorate.domain.model.Server
import com.tristaam.aovherorate.domain.repository.ServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ServerRepositoryImpl(
    private val serverDao: ServerDao
) : ServerRepository {
    override fun getAllServers(): Flow<List<Server>> {
        return serverDao.getAllServerEntities().map { serverEntities ->
            serverEntities.map { it.toServer() }
        }.flowOn(Dispatchers.IO)
    }
}