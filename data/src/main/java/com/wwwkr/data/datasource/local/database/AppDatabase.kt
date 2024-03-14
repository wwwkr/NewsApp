package com.wwwkr.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wwwkr.data.datasource.local.database.dao.NewsDao
import com.wwwkr.data.datasource.local.database.entity.NewsEntity

@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = true,
    autoMigrations = [
        /**
         *  ex) AutoMigration (from = 1, to 2)
         */
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun NewsDao() : NewsDao
}