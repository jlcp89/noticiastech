package com.d3sarrollo.newsapp.domain.usecases.news

import com.d3sarrollo.newsapp.data.local.NewsDao
import com.d3sarrollo.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val newsDao: NewsDao
) {

    operator fun invoke(): Flow<List<Article>>{
        return newsDao.getArticles()
    }

}