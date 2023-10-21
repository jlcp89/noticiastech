package com.d3sarrollo.newsapp.domain.usecases.news

import com.d3sarrollo.newsapp.data.local.NewsDao
import com.d3sarrollo.newsapp.domain.model.Article

class UpsertArticle(
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(article: Article){
        newsDao.upsert(article = article)
    }

}