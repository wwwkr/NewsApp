package com.wwwkr.data.datasource.local

import com.wwwkr.data.datasource.local.database.AppDatabase
import com.wwwkr.data.datasource.local.database.entity.NewsEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsLocalSource @Inject constructor(private val appDatabase: AppDatabase) {

   suspend fun insertNews(item : NewsEntity) = flow {
      emit(appDatabase.NewsDao().insert(item = item))
   }

   suspend fun selectNews() = flow {
      emit(appDatabase.NewsDao().select())
   }

   suspend fun deleteNews(title: String) = flow {
      emit(appDatabase.NewsDao().deleteItem(title = title))
   }
}