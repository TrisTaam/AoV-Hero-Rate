package com.tristaam.aovherorate.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Base repository class that encapsulates the common pattern of:
 * 1. Fetching entities from a DAO as Flow
 * 2. Mapping entities to domain models
 * 3. Applying IO dispatcher
 */
abstract class BaseFlowRepository<Entity, Domain> {
    
    protected fun mapFlow(
        sourceFlow: Flow<List<Entity>>,
        mapper: (Entity) -> Domain
    ): Flow<List<Domain>> {
        return sourceFlow
            .map { entities -> entities.map(mapper) }
            .flowOn(Dispatchers.IO)
    }
}
