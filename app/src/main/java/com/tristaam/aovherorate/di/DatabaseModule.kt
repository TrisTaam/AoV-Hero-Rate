package com.tristaam.aovherorate.di

import androidx.room.Room
import com.tristaam.aovherorate.data.source.local.AoVHeroRateDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<AoVHeroRateDatabase> {
        Room.databaseBuilder(
            get(),
            AoVHeroRateDatabase::class.java,
            AoVHeroRateDatabase.DATABASE_NAME
        ).build()
    }

    single { get<AoVHeroRateDatabase>().heroDao }

    single { get<AoVHeroRateDatabase>().heroRateDao }

    single { get<AoVHeroRateDatabase>().heroTypeDao }

    single { get<AoVHeroRateDatabase>().gameModeDao }

    single { get<AoVHeroRateDatabase>().rankDao }

    single { get<AoVHeroRateDatabase>().gameModeRankDao }
}