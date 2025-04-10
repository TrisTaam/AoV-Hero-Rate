package com.tristaam.aovherorate.di

import com.tristaam.aovherorate.data.repository.GameModeRepositoryImpl
import com.tristaam.aovherorate.data.repository.HeroRateRepositoryImpl
import com.tristaam.aovherorate.data.repository.HeroTypeRepositoryImpl
import com.tristaam.aovherorate.data.repository.RankRepositoryImpl
import com.tristaam.aovherorate.data.repository.RemoteRepositoryImpl
import com.tristaam.aovherorate.data.repository.ServerRepositoryImpl
import com.tristaam.aovherorate.domain.repository.GameModeRepository
import com.tristaam.aovherorate.domain.repository.HeroRateRepository
import com.tristaam.aovherorate.domain.repository.HeroTypeRepository
import com.tristaam.aovherorate.domain.repository.RankRepository
import com.tristaam.aovherorate.domain.repository.RemoteRepository
import com.tristaam.aovherorate.domain.repository.ServerRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::RemoteRepositoryImpl) bind RemoteRepository::class

    singleOf(::GameModeRepositoryImpl) bind GameModeRepository::class

    singleOf(::HeroTypeRepositoryImpl) bind HeroTypeRepository::class

    singleOf(::RankRepositoryImpl) bind RankRepository::class

    singleOf(::HeroRateRepositoryImpl) bind HeroRateRepository::class

    singleOf(::ServerRepositoryImpl) bind ServerRepository::class
}