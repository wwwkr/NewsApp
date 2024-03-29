package com.wwwkr.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.usecase.DeleteScrapNewsUseCase
import com.wwwkr.domain.usecase.GetNewsUseCase
import com.wwwkr.domain.usecase.GetScrapNewsUseCase
import com.wwwkr.domain.usecase.InsertScrapNewsUseCase
import com.wwwkr.domain.usecase.UpdateMemoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getScrapNewsUseCase: GetScrapNewsUseCase,
    private val insertScrapNewsUseCase: InsertScrapNewsUseCase,
    private val deleteScrapNewsUseCase: DeleteScrapNewsUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase
    ) : ViewModel() {

    val newsStateFlow = getNewsUseCase.getNewsStateFlow
    val scrapNewsStateFlow = getScrapNewsUseCase.getScrapNewsStateFlow

    var selectItem: ArticleModel? = null

    fun getNews(country : String) = viewModelScope.launch {
        getNewsUseCase(country = country)
    }

    fun getScrapNews() = viewModelScope.launch {
        getScrapNewsUseCase()
    }
    fun insertNews(item: ArticleModel) = viewModelScope.launch {
        insertScrapNewsUseCase(item = item.copy(isScraped = true))
    }

    fun deleteNews(item: ArticleModel, isScrapView: Boolean) = viewModelScope.launch {
        deleteScrapNewsUseCase.invoke(item = item, isScrapView = isScrapView)
    }

    fun updateMemo(title: String, memo: String) = viewModelScope.launch {
        updateMemoUseCase.invoke(title = title, memo = memo)
    }

}