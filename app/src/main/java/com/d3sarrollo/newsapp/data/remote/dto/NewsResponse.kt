package com.d3sarrollo.newsapp.data.remote.dto

import com.d3sarrollo.newsapp.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)