package com.tristaam.aovherorate.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tristaam.aovherorate.BuildConfig
import com.tristaam.aovherorate.data.source.local.dao.GameModeDao
import com.tristaam.aovherorate.data.source.local.dao.GameModeRankDao
import com.tristaam.aovherorate.data.source.local.dao.HeroDao
import com.tristaam.aovherorate.data.source.local.dao.HeroRateDao
import com.tristaam.aovherorate.data.source.local.dao.HeroTypeDao
import com.tristaam.aovherorate.data.source.local.dao.RankDao
import com.tristaam.aovherorate.data.source.local.dao.ServerDao
import com.tristaam.aovherorate.data.source.local.entity.GameModeEntity
import com.tristaam.aovherorate.data.source.local.entity.GameModeRankCrossRef
import com.tristaam.aovherorate.data.source.local.entity.HeroEntity
import com.tristaam.aovherorate.data.source.local.entity.HeroRateEntity
import com.tristaam.aovherorate.data.source.local.entity.HeroTypeEntity
import com.tristaam.aovherorate.data.source.local.entity.RankEntity
import com.tristaam.aovherorate.data.source.local.entity.ServerEntity

@Database(
    entities = [
        HeroEntity::class,
        HeroTypeEntity::class,
        RankEntity::class,
        GameModeEntity::class,
        HeroRateEntity::class,
        GameModeRankCrossRef::class,
        ServerEntity::class,
    ],
    version = 2
)
abstract class AoVHeroRateDatabase : RoomDatabase() {
    abstract val heroDao: HeroDao
    abstract val heroTypeDao: HeroTypeDao
    abstract val rankDao: RankDao
    abstract val gameModeDao: GameModeDao
    abstract val heroRateDao: HeroRateDao
    abstract val gameModeRankDao: GameModeRankDao
    abstract val serverDao: ServerDao

    companion object {
        const val DATABASE_NAME = "AoVHeroRate.db"
        val callback = object : Callback() {
            override fun onCreate(database: SupportSQLiteDatabase) {
                super.onCreate(database)
                database.execSQL(
                    """
                    INSERT INTO server (id, name, type, base_url) VALUES
                    ('1', 'Việt Nam', 'vn', '${BuildConfig.BASE_URL_VN}'),
                    ('2', 'Thái Lan', 'th', '${BuildConfig.BASE_URL_TH}'),
                    ('3', 'Đài Loan', 'tw', '${BuildConfig.BASE_URL_TW}');
                """.trimIndent()
                )
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS server (
                        id TEXT NOT NULL PRIMARY KEY,
                        name TEXT NOT NULL,
                        type TEXT NOT NULL,
                        base_url TEXT NOT NULL
                    )
                """.trimIndent()
                )

                database.execSQL(
                    """
                    INSERT INTO server (id, name, type, base_url) VALUES
                    ('1', 'Việt Nam', 'vn', '${BuildConfig.BASE_URL_VN}'),
                    ('2', 'Thái Lan', 'th', '${BuildConfig.BASE_URL_TH}'),
                    ('3', 'Đài Loan', 'tw', '${BuildConfig.BASE_URL_TW}');
                """.trimIndent()
                )

                database.execSQL(
                    """
                    CREATE TABLE hero_rate_new (
                        hero_id TEXT NOT NULL,
                        server_id TEXT NOT NULL,
                        game_mode_id TEXT NOT NULL,
                        rank_id TEXT NOT NULL,
                        win_rate REAL,
                        pick_rate REAL,
                        ban_rate REAL,
                        PRIMARY KEY (hero_id, server_id, game_mode_id, rank_id),
                        FOREIGN KEY (hero_id) REFERENCES hero(id) ON DELETE CASCADE,
                        FOREIGN KEY (server_id) REFERENCES server(id) ON DELETE CASCADE,
                        FOREIGN KEY (game_mode_id) REFERENCES game_mode(id) ON DELETE CASCADE,
                        FOREIGN KEY (rank_id) REFERENCES rank(id) ON DELETE CASCADE
                    );
                """.trimIndent()
                )

                database.execSQL(
                    """
                    INSERT INTO hero_rate_new (
                        hero_id, server_id, game_mode_id, rank_id, win_rate, pick_rate, ban_rate
                    )
                    SELECT hero_id, '1', game_mode_id, rank_id, win_rate, pick_rate, ban_rate
                    FROM hero_rate;
                    """.trimIndent()
                )

                database.execSQL("DROP TABLE hero_rate")
                database.execSQL("ALTER TABLE hero_rate_new RENAME TO hero_rate")
            }
        }
    }
}