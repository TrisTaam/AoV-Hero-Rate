package com.tristaam.aovherorate.data.repository

import com.tristaam.aovherorate.data.mapper.toServer
import com.tristaam.aovherorate.data.source.local.dao.ServerDao
import com.tristaam.aovherorate.data.source.local.entity.ServerEntity
import com.tristaam.aovherorate.domain.model.Server
import com.tristaam.aovherorate.domain.repository.ServerRepository
import kotlinx.coroutines.flow.Flow

class ServerRepositoryImpl(
    private val serverDao: ServerDao
) : BaseFlowRepository<ServerEntity, Server>(), ServerRepository {
    override fun getAllServers(): Flow<List<Server>> {
        return mapFlow(serverDao.getAllServerEntities()) { it.toServer() }
    }
}