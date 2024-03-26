package com.wwwkr.data.datasource.local

import com.wwwkr.data.datasource.local.database.AppDatabase
import com.wwwkr.data.datasource.local.database.entity.NewsEntity
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(private val database: AppDatabase) : NewsLocalDataSource {

   override val getScrapNewsStateFlow = MutableStateFlow<UiState<List<ArticleModel>>>(UiState.Empty)

   override suspend fun insertNews(item : NewsEntity) = flow {
      emit(database.NewsDao().insert(item = item))
   }

   override suspend fun selectNews() = flow {
      emit(database.NewsDao().select())
   }

   override suspend fun deleteNews(title: String) = flow {
      emit(database.NewsDao().deleteItem(title = title))
   }

   override suspend fun updateNews(title: String, memo: String) = flow {
      emit(database.NewsDao().updateMemo(title = title, memo = memo))
   }

}