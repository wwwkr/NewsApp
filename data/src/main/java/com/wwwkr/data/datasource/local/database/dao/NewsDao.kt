package com.wwwkr.data.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wwwkr.data.datasource.local.database.entity.NewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: NewsEntity)

    @Query("select * from News")
    suspend fun select(): List<NewsEntity>

    @Query("delete from News where title = :title")
    suspend fun deleteItem(title: String)
}