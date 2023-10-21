package com.d3sarrollo.newsapp.domain.usecases.news

import com.d3sarrollo.newsapp.data.local.NewsDao
import com.d3sarrollo.newsapp.domain.model.Article

class GetArticle (
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(url: String): Article?{
        return newsDao.getArticle(url = url)
    }

}